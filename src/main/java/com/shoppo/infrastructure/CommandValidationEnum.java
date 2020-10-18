package com.shoppo.infrastructure;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;
public enum CommandValidationEnum {
    LAUNCH_JAVA_JAR("java -jar -Dserver.port=%s %s > %s.log 2>&1"),
    CMD_EXE("cmd.exe"),
    WINDOWS_NEEDED_ARGS("/c");

    String cli;

    CommandValidationEnum(String cli) {
        this.cli = cli;
    }

    public static CommandValidationEnum findByKey(String cli_flagname) {
        return findBy(element -> element.name().equals(cli_flagname));
    }

    private static CommandValidationEnum findBy(Predicate<CommandValidationEnum> predicat)  {
        return Arrays.stream(values())
                .filter(predicat)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknwon command."));
    }

    public String translate(Object... args){

        return format(this.cli,Arrays.stream(args)
                .map(e ->HtmlUtils.htmlEscape(e!=null ? e.toString() : ""))
                .map(StringEscapeUtils::escapeJava)
                .map(StringEscapeUtils::escapeJson)
                .map(StringEscapeUtils::escapeEcmaScript)
                .map(StringEscapeUtils::escapeXSI)
                .map(StringEscapeUtils::escapeHtml3)
                .map(StringEscapeUtils::escapeHtml4)
                .map(StringEscapeUtils::escapeXml11)
                .toArray());
    }

}
