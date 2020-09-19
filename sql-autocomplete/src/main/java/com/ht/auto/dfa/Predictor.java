package com.ht.auto.dfa;

import com.google.common.collect.Maps;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author canbin.zhang@qq.com
 */
public class Predictor {
    public final static Logger logger = LoggerFactory.getLogger(Predictor.class);


    public static class CandidatesCollection {
        public Map<Integer, List<Integer>> tokens = Maps.newHashMap();
        public Map<Integer, Integer> tokenRule = Maps.newHashMap();
        public Map<Integer, List<Integer>> rules = Maps.newHashMap();
        public Map<Integer, List<Integer>> rulePositions = Maps.newHashMap();

        @Override
        public String toString() {
            return "CandidatesCollection{" + "tokens=" + tokens + ", rules=" + rules + ", ruleStrings=" + rulePositions + '}';
        }
    }

    public static class FollowSetWithPath {
        public IntervalSet intervals;
        public List<Integer> path;
        public List<Integer> following;
    }

    public static class FollowSetsHolder {
        public List<FollowSetWithPath> sets;
        public IntervalSet combined;
    }

    public static class PipelineEntry {

        public PipelineEntry(ATNState state, Integer tokenIndex) {
            this.state = state;
            this.tokenIndex = tokenIndex;
        }

        ATNState state;
        Integer tokenIndex;
    }

    private Set<Integer> ignoredTokens = new HashSet<>();
    private Set<Integer> preferredRules = new HashSet<>();

    private final Parser parser;
    private final ATN atn;
    private Vocabulary vocabulary;
    private String[] ruleNames;
    private List<Token> tokens;

    private final Map<Integer, Map<Integer, Set<Integer>>> shortcutMap = new HashMap<>();
    private final CandidatesCollection candidates = new CandidatesCollection();

    private final Map<String, Map<Integer, FollowSetsHolder>> followSetsByATN = new HashMap<>();

    public Predictor(Parser parser, Set<Integer> preferredRules, Set<Integer> ignoredTokens) {
        this.parser = parser;
        this.atn = parser.getATN();
        this.vocabulary = parser.getVocabulary();
        this.ruleNames = parser.getRuleNames();
        if (preferredRules != null) {
            this.preferredRules = preferredRules;
        }
        if (ignoredTokens != null) {
            this.ignoredTokens = ignoredTokens;
        }
    }

    public Set<Integer> getPreferredRules() {
        return Collections.unmodifiableSet(preferredRules);
    }

    public void setPreferredRules(Set<Integer> preferredRules) {
        this.preferredRules = new HashSet<>(preferredRules);
    }

    public CandidatesCollection collectCandidates(int caretTokenIndex, ParserRuleContext context) {
        this.shortcutMap.clear();
        this.candidates.rules.clear();
        this.candidates.tokens.clear();

        int tokenStartIndex = context != null ? context.start.getTokenIndex() : 0;
        TokenStream tokenStream = this.parser.getInputStream();

        int currentIndex = tokenStream.index();
        tokenStream.seek(tokenStartIndex);
        this.tokens = new LinkedList<>();
        int offset = 1;
        while (true) {
            Token token = tokenStream.LT(offset++);
            this.tokens.add(token);
            if (token.getTokenIndex() >= caretTokenIndex || token.getType() == Token.EOF) {
                break;
            }
        }
        tokenStream.seek(currentIndex);

        LinkedList<Integer> callStack = new LinkedList<>();
        int startRule = context != null ? context.getRuleIndex() : 0;
        this.processRule(this.atn.ruleToStartState[startRule], 0, callStack, "\n");

        tokenStream.seek(currentIndex);

        for (int ruleId : preferredRules) {
            final Map<Integer, Set<Integer>> shortcut = shortcutMap.get(ruleId);
            if (shortcut == null || shortcut.isEmpty()) {
                continue;
            }
            // select the right-most occurrence
            final int startToken = Collections.max(shortcut.keySet());
            final Set<Integer> endSet = shortcut.get(startToken);
            final int endToken;
            if (endSet.isEmpty()) {
                endToken = tokens.size() - 1;
            } else {
                endToken = Collections.max(shortcut.get(startToken));
            }
            final int startOffset = tokens.get(startToken).getStartIndex();
            final int endOffset;
            if (tokens.get(endToken).getType() == Token.EOF) {
                endOffset = tokens.get(endToken).getStartIndex();
            } else {
                endOffset = tokens.get(endToken - 1).getStopIndex() + 1;
            }

            final List<Integer> ruleStartStop = Arrays.asList(startOffset, endOffset);
            candidates.rulePositions.put(ruleId, ruleStartStop);
        }

        return this.candidates;
    }


    private boolean checkPredicate(PredicateTransition transition) {
        return transition.getPredicate().eval(this.parser, ParserRuleContext.EMPTY);
    }

    private boolean translateToRuleIndex(List<Integer> ruleStack) {
        if (this.preferredRules.isEmpty()) {
            return false;
        }

        for (int i = 0; i < ruleStack.size(); ++i) {
            if (this.preferredRules.contains(ruleStack.get(i))) {
                List<Integer> path = new LinkedList<>(ruleStack.subList(0, i));
                boolean addNew = true;
                for (Map.Entry<Integer, List<Integer>> entry : this.candidates.rules.entrySet()) {
                    if (!entry.getKey().equals(ruleStack.get(i)) || entry.getValue().size() != path.size()) {
                        continue;
                    }
                    if (path.equals(entry.getValue())) {
                        addNew = false;
                        break;
                    }
                }

                if (addNew) {
                    this.candidates.rules.put(ruleStack.get(i), path);
                }
                return true;
            }
        }

        return false;
    }

    private List<Integer> getFollowingTokens(Transition initialTransition) {
        LinkedList<Integer> result = new LinkedList<>();
        LinkedList<ATNState> pipeline = new LinkedList<>();
        pipeline.add(initialTransition.target);

        while (!pipeline.isEmpty()) {
            ATNState state = pipeline.removeLast();

            for (Transition transition : state.getTransitions()) {
                if (transition.getSerializationType() == Transition.ATOM) {
                    if (!transition.isEpsilon()) {
                        List<Integer> list = transition.label().toList();
                        if (list.size() == 1 && !this.ignoredTokens.contains(list.get(0))) {
                            result.addLast(list.get(0));
                            pipeline.addLast(transition.target);
                        }
                    } else {
                        pipeline.addLast(transition.target);
                    }
                }
            }
        }

        return result;
    }

    private LinkedList<FollowSetWithPath> determineFollowSets(ATNState start, ATNState stop) {
        LinkedList<FollowSetWithPath> result = new LinkedList<>();
        Set<ATNState> seen = new HashSet<>();
        LinkedList<Integer> ruleStack = new LinkedList<>();

        this.collectFollowSets(start, stop, result, seen, ruleStack);

        return result;
    }

    private void collectFollowSets(ATNState s, ATNState stopState, LinkedList<FollowSetWithPath> followSets,
                                   Set<ATNState> seen, LinkedList<Integer> ruleStack) {

        if (seen.contains(s)) {
            return;
        }
        seen.add(s);
        if (s.equals(stopState) || s.getStateType() == ATNState.RULE_STOP) {
            FollowSetWithPath set = new FollowSetWithPath();
            set.intervals = IntervalSet.of(Token.EPSILON);
            set.path = new LinkedList<>(ruleStack);
            followSets.addLast(set);
            return;
        }

        for (Transition transition : s.getTransitions()) {
            if (transition.getSerializationType() == Transition.RULE) {
                RuleTransition ruleTransition = (RuleTransition) transition;
                if (ruleStack.contains(ruleTransition.target.ruleIndex)) {
                    continue;
                }
                ruleStack.addLast(ruleTransition.target.ruleIndex);
                this.collectFollowSets(transition.target, stopState, followSets, seen, ruleStack);
                ruleStack.removeLast();

            } else if (transition.getSerializationType() == Transition.PREDICATE) {
                if (this.checkPredicate((PredicateTransition) transition)) {
                    this.collectFollowSets(transition.target, stopState, followSets, seen, ruleStack);
                }
            } else if (transition.isEpsilon()) {
                this.collectFollowSets(transition.target, stopState, followSets, seen, ruleStack);
            } else if (transition.getSerializationType() == Transition.WILDCARD) {
                FollowSetWithPath set = new FollowSetWithPath();
                set.intervals = IntervalSet.of(Token.MIN_USER_TOKEN_TYPE, this.atn.maxTokenType);
                set.path = new LinkedList<>(ruleStack);
                followSets.addLast(set);
            } else {
                IntervalSet label = transition.label();
                if (label != null && label.size() > 0) {
                    if (transition.getSerializationType() == Transition.NOT_SET) {
                        label = label.complement(IntervalSet.of(Token.MIN_USER_TOKEN_TYPE, this.atn.maxTokenType));
                    }
                    FollowSetWithPath set = new FollowSetWithPath();
                    set.intervals = label;
                    set.path = new LinkedList<>(ruleStack);
                    set.following = this.getFollowingTokens(transition);
                    followSets.addLast(set);
                }
            }
        }
    }

    private Set<Integer> processRule(ATNState startState, int tokenIndex, LinkedList<Integer> callStack, String indentation) {
        Map<Integer, Set<Integer>> positionMap = this.shortcutMap.get(startState.ruleIndex);
        if (positionMap == null) {
            positionMap = new HashMap<>();
            this.shortcutMap.put(startState.ruleIndex, positionMap);
        } else {
            if (positionMap.containsKey(tokenIndex)) {
                return positionMap.get(tokenIndex);
            }
        }
        Set<Integer> result = new HashSet<>();
        Map<Integer, FollowSetsHolder> setsPerState = followSetsByATN.computeIfAbsent(this.parser.getClass().getName(),
                k -> new HashMap<>());

        FollowSetsHolder followSets = setsPerState.get(startState.stateNumber);
        if (followSets == null) {
            followSets = new FollowSetsHolder();
            setsPerState.put(startState.stateNumber, followSets);
            RuleStopState stop = this.atn.ruleToStopState[startState.ruleIndex];
            followSets.sets = this.determineFollowSets(startState, stop);

            IntervalSet combined = new IntervalSet();
            for (FollowSetWithPath set : followSets.sets) {
                combined.addAll(set.intervals);
            }
            followSets.combined = combined;
        }

        callStack.addLast(startState.ruleIndex);
        int currentSymbol = this.tokens.get(tokenIndex).getType();

        if (tokenIndex >= this.tokens.size() - 1) {
            if (this.preferredRules.contains(startState.ruleIndex)) {
                this.translateToRuleIndex(callStack);
            } else {
                for (FollowSetWithPath set : followSets.sets) {
                    LinkedList<Integer> fullPath = new LinkedList<>(callStack);
                    fullPath.addAll(set.path);
                    if (!this.translateToRuleIndex(fullPath)) {
                        for (int symbol : set.intervals.toList()) {
                            if (!this.ignoredTokens.contains(symbol)) {
                                if (!this.candidates.tokens.containsKey(symbol)) {
                                    this.candidates.tokens.put(symbol, set.following);
                                } else {
                                    if (!this.candidates.tokens.get(symbol).equals(set.following)) {
                                        this.candidates.tokens.put(symbol, new LinkedList<>());
                                    }
                                }
                            } else {
                                logger.info("====> collection: Ignoring token: " + symbol);
                            }
                        }
                    }
                }
            }
            callStack.removeLast();
            return result;
        } else {
            if (!followSets.combined.contains(Token.EPSILON) && !followSets.combined.contains(currentSymbol)) {
                callStack.removeLast();
                return result;
            }
        }
        LinkedList<PipelineEntry> statePipeline = new LinkedList<>();
        PipelineEntry currentEntry;

        statePipeline.add(new PipelineEntry(startState, tokenIndex));

        while (!statePipeline.isEmpty()) {
            currentEntry = statePipeline.removeLast();

            currentSymbol = this.tokens.get(currentEntry.tokenIndex).getType();

            boolean atCaret = currentEntry.tokenIndex >= this.tokens.size() - 1;

            switch (currentEntry.state.getStateType()) {
                case ATNState.RULE_START:
                    indentation += "  ";
                    break;
                case ATNState.RULE_STOP: {
                    result.add(currentEntry.tokenIndex);
                    continue;
                }
                default:
                    break;
            }

            Transition[] transitions = currentEntry.state.getTransitions();
            for (Transition transition : transitions) {
                switch (transition.getSerializationType()) {
                    case Transition.RULE: {
                        Set<Integer> endStatus = this.processRule(transition.target, currentEntry.tokenIndex, callStack, indentation);
                        for (Integer position : endStatus) {
                            statePipeline.addLast(new PipelineEntry(((RuleTransition) transition).followState, position));
                        }
                        break;
                    }

                    case Transition.PREDICATE: {
                        if (this.checkPredicate((PredicateTransition) transition)) {
                            statePipeline.addLast(new PipelineEntry(transition.target, currentEntry.tokenIndex));
                        }
                        break;
                    }

                    case Transition.WILDCARD: {
                        if (atCaret) {
                            if (!this.translateToRuleIndex(callStack)) {
                                for (Integer token : IntervalSet.of(Token.MIN_USER_TOKEN_TYPE, this.atn.maxTokenType).toList()) {
                                    if (!this.ignoredTokens.contains(token)) {
                                        this.candidates.tokens.put(token, new LinkedList<>());
                                    }
                                }
                            }
                        } else {
                            statePipeline.addLast(new PipelineEntry(transition.target, currentEntry.tokenIndex + 1));
                        }
                        break;
                    }

                    default: {
                        if (transition.isEpsilon()) {
                            statePipeline.addLast(new PipelineEntry(transition.target, currentEntry.tokenIndex));
                            continue;
                        }

                        IntervalSet set = transition.label();
                        if (set != null && set.size() > 0) {
                            if (transition.getSerializationType() == Transition.NOT_SET) {
                                set = set.complement(IntervalSet.of(Token.MIN_USER_TOKEN_TYPE, this.atn.maxTokenType));
                            }
                            if (atCaret) {
                                if (!this.translateToRuleIndex(callStack)) {
                                    List<Integer> list = set.toList();
                                    boolean addFollowing = list.size() == 1;
                                    for (Integer symbol : list) {
                                        if (!this.ignoredTokens.contains(symbol)) {
                                            if (addFollowing) {
                                                this.candidates.tokens.put(symbol, this.getFollowingTokens(transition));
                                                this.candidates.tokenRule.put(symbol, this.getPreferredRulesOfToken(transition));
                                            } else {
                                                this.candidates.tokens.put(symbol, new LinkedList<>());
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (set.contains(currentSymbol)) {
                                    statePipeline.addLast(new PipelineEntry(transition.target, currentEntry.tokenIndex + 1));
                                }
                            }
                        }
                    }
                }
            }
        }
        callStack.removeLast();
        positionMap.put(tokenIndex, result);

        return result;
    }

    private int getPreferredRulesOfToken(Transition transition) {
        return transition.target.ruleIndex;
    }
}
