package com.shoppo.domain;

import ch.qos.logback.core.util.FileUtil;
import com.shoppo.infrastructure.CommandValidationEnum;
import com.shoppo.infrastructure.ProcessHandler;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.IOException;

import static com.shoppo.infrastructure.CommandValidationEnum.*;


public abstract class Instance {

    private String name;
    private int serverPort;
    private final File jar;


/*TODO a scanner which check if a file with the name already exist or not*/
    public Instance(File jar){
        this.jar = jar;
    }

    public void launch() throws IOException {
        File copy = buildPathFile(jar.getParentFile());
        FileCopyUtils.copy(jar,copy);
        Process proc ;
        String cli =
                LAUNCH_JAVA_JAR
                        .translate(
                            this.serverPort,
                            copy.getAbsolutePath(),
                            name);

        if (SystemUtils.IS_OS_WINDOWS)
            proc = Runtime
                .getRuntime()
                .exec(
                        new String[]{
                                CMD_EXE.translate(),
                                WINDOWS_NEEDED_ARGS.translate(),
                                cli
                        });
        else
            proc = Runtime.getRuntime().exec(cli);

        System.out.printf("Program launch with pid : %s on port : %s%n",proc.pid(),this.serverPort);
        /* to avoid deadlock */
        ProcessHandler inputStream = new ProcessHandler(proc.getInputStream(), "INPUT");
        ProcessHandler errorStream = new ProcessHandler(proc.getErrorStream(), "ERROR");
        /* start the stream threads */
        inputStream.start();
        errorStream.start();

    }

    public Instance name(String name){
        this.name = name;
        return this;
    }

    public Instance serverPort(int serverPort){
        this.serverPort = serverPort;
        return this;
    }
/*A*/
    private File buildPathFile(File parent){
        if(name == null || !name.matches("[A-Za-z]{0,20}"))
            throw new IllegalArgumentException("invalid name!");
        if( parent == null || !parent.isDirectory() )
            throw new IllegalArgumentException("Invalid parent file input");

        return new File(
                HtmlUtils.htmlEscape(parent.getAbsolutePath()
                        .concat(File.separator)
                            .concat(name.concat(".jar"))));
    }
}
