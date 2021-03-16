package com.sabo;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.apache.tika.sax.ToXMLContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.sax.xpath.Matcher;
import org.apache.tika.sax.xpath.MatchingContentHandler;
import org.apache.tika.sax.xpath.XPathParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TikaTemplate {
    private static final int MAXIMUM_TEXT_CHUNK_SIZE = 4096;

    // Tika facade
    public String parseToStringExample(String file) throws IOException, SAXException, TikaException {
        Tika tika = new Tika();
        try (InputStream stream = TikaTemplate.class.getResourceAsStream(file)) {
            return tika.parseToString(stream);
        }
    }

    // Auto-Detect Parser
    public String parseExample(String file) throws IOException, SAXException, TikaException {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        try (InputStream stream = TikaTemplate.class.getResourceAsStream(file)) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }

    // Parsing to Plain Text
    public String parseToPlainText(String file) throws IOException, SAXException, TikaException {
        BodyContentHandler handler = new BodyContentHandler();

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = TikaTemplate.class.getResourceAsStream(file)) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }

    // Parsing to XHTML
    public String parseToHTML(String file) throws IOException, SAXException, TikaException {
        ContentHandler handler = new ToXMLContentHandler();

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = TikaTemplate.class.getResourceAsStream(file)) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }

    // just want the body of the xhtml document
    public String parseBodyToHTML(String file) throws IOException, SAXException, TikaException {
        ContentHandler handler = new BodyContentHandler(
                new ToXMLContentHandler());

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = TikaTemplate.class.getResourceAsStream(file)) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }

    // execute XPath queries on the parse results
    public String parseOnePartToHTML(String file) throws IOException, SAXException, TikaException {
        // Only get things under html -> body -> div (class=header)
        XPathParser xhtmlParser = new XPathParser("xhtml", XHTMLContentHandler.XHTML);
        Matcher divContentMatcher = xhtmlParser.parse("/xhtml:html/xhtml:body/xhtml:div/descendant::node()");
        ContentHandler handler = new MatchingContentHandler(
                new ToXMLContentHandler(), divContentMatcher);

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = TikaTemplate.class.getResourceAsStream(file)) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }

//    Sometimes, you want to chunk the resulting text up, perhaps to output as you go minimising memory use,
//    perhaps to output to HDFS files, or any other reason! With a small custom content handler, you can do that.
    public List<String> parseToPlainTextChunks(String file) throws IOException, SAXException, TikaException {
        final List<String> chunks = new ArrayList<>();
        chunks.add("");
        ContentHandlerDecorator handler = new ContentHandlerDecorator() {
            @Override
            public void characters(char[] ch, int start, int length) {
                String lastChunk = chunks.get(chunks.size() - 1);
                String thisStr = new String(ch, start, length);

                if (lastChunk.length() + length > MAXIMUM_TEXT_CHUNK_SIZE) {
                    chunks.add(thisStr);
                } else {
                    chunks.set(chunks.size() - 1, lastChunk + thisStr);
                }
            }
        };

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = TikaTemplate.class.getResourceAsStream(file)) {
            parser.parse(stream, handler, metadata);
            return chunks;
        }
    }

    // 使用微软的翻译
//    public String microsoftTranslateToFrench(String text) {
//        MicrosoftTranslator translator = new MicrosoftTranslator();
//        // Change the id and secret! See http://msdn.microsoft.com/en-us/library/hh454950.aspx.
//        translator.setId("dummy-id");
//        translator.setSecret("dummy-secret");
//        try {
//            return translator.translate(text, "fr");
//        } catch (Exception e) {
//            return "Error while translating.";
//        }
//    }

    // 识别语种
    public String identifyLanguage(String text) {
        LanguageIdentifier identifier = new LanguageIdentifier(text);
        return identifier.getLanguage();
    }

    public static void main(String[] args) throws Exception {
        new TikaTemplate().parseToStringExample("a.dbf");
    }
}
