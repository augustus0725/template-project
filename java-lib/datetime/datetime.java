# Java 8以上, 自带
DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");
String currentDateTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).format(outputFormat);
String endDateTime = LocalDateTime.now().plusYears(100).atZone(ZoneId.of("Asia/Shanghai")).format(outputFormat);