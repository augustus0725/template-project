# guava的CharMatcher
assertEquals("hel*lo", CharMatcher.anyOf("*").trimFrom("*hel*lo*"));

# java Pattern
final Pattern IP_PATTERN = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
Matcher m = IP_PATTERN.matcher("192.1.3.2:");

assertTrue(m.find());
assertEquals("192.1.3.2", m.group());