package cn.zzq.aix.builder.compiler;

import java.io.File;
import java.io.PrintWriter;

import org.eclipse.jdt.internal.compiler.batch.Main;

import cn.zzq.aix.builder.utils.CommandBuilder;
import cn.zzq.aix.builder.utils.Logger;
import cn.zzq.aix.builder.utils.Path;

/*
 Eclipse Compiler for Java(TM) v20150902-1521, 3.11.1
 Copyright IBM Corp 2000, 2015. All rights reserved.

 Usage: <options> <source files | directories>
 If directories are specified, then their source contents are compiled.
 Possible options are listed below. Options enabled by default are prefixed
 with '+'.

 Classpath options:
 -cp -classpath <directories and ZIP archives separated by ;>
 specify location for application classes and sources.
 Each directory or file can specify access rules for
 types between '[' and ']' (e.g. [-X] to forbid
 access to type X, [~X] to discourage access to type X,
 [+p/X;-p/*] to forbid access to all types in package p
 but allow access to p/X)
 -bootclasspath <directories and ZIP archives separated by ;>
 specify location for system classes. Each directory or
 file can specify access rules for types between '['
 and ']'
 -sourcepath <directories and ZIP archives separated by ;>
 specify location for application sources. Each directory
 or file can specify access rules for types between '['
 and ']'. Each directory can further specify a specific
 destination directory using a '-d' option between '['
 and ']'; this overrides the general '-d' option.
 .class files created from source files contained in a
 jar file are put in the user.dir folder in case no
 general '-d' option is specified. ZIP archives cannot
 override the general '-d' option
 -extdirs <directories separated by ;>
 specify location for extension ZIP archives
 -endorseddirs <directories separated by ;>
 specify location for endorsed ZIP archives
 -d <dir>           destination directory (if omitted, no directory is
 created); this option can be overridden per source
 directory
 -d none            generate no .class files
 -encoding <enc>    specify default encoding for all source files. Each
 file/directory can override it when suffixed with
 '['<enc>']' (e.g. X.java[utf8]).
 If multiple default encodings are specified, the last
 one will be used.

 Compliance options:
 -1.3               use 1.3 compliance (-source 1.3 -target 1.1)
 -1.4             + use 1.4 compliance (-source 1.3 -target 1.2)
 -1.5 -5 -5.0       use 1.5 compliance (-source 1.5 -target 1.5)
 -1.6 -6 -6.0       use 1.6 compliance (-source 1.6 -target 1.6)
 -1.7 -7 -7.0       use 1.7 compliance (-source 1.7 -target 1.7)
 -1.8 -8 -8.0       use 1.8 compliance (-source 1.8 -target 1.8)
 -source <version>  set source level: 1.3 to 1.8 (or 5, 5.0, etc)
 -target <version>  set classfile target: 1.1 to 1.8 (or 5, 5.0, etc)
 cldc1.1 can also be used to generate the StackMap
 attribute

 Warning options:
 -deprecation     + deprecation outside deprecated code (equivalent to
 -warn:+deprecation)
 -nowarn -warn:none disable all warnings
 -nowarn:[<directories separated by ;>]
 specify directories from which optional problems should
 be ignored
 -?:warn -help:warn display advanced warning options

 Error options:
 -err:<warnings separated by ,>    convert exactly the listed warnings
 to be reported as errors
 -err:+<warnings separated by ,>   enable additional warnings to be
 reported as errors
 -err:-<warnings separated by ,>   disable specific warnings to be
 reported as errors

 Setting warning or error options using properties file:
 -properties <file>   set warnings/errors option based on the properties
 file contents. This option can be used with -nowarn,
 -err:.. or -warn:.. options, but the last one on the
 command line sets the options to be used.

 Debug options:
 -g[:lines,vars,source] custom debug info
 -g:lines,source  + both lines table and source debug info
 -g                 all debug info
 -g:none            no debug info
 -preserveAllLocals preserve unused local vars for debug purpose

 Annotation processing options:
 These options are meaningful only in a 1.6 environment.
 -Akey[=value]        options that are passed to annotation processors
 -processorpath <directories and ZIP archives separated by ;>
 specify locations where to find annotation processors.
 If this option is not used, the classpath will be
 searched for processors
 -processor <class1[,class2,...]>
 qualified names of the annotation processors to run.
 This bypasses the default annotation discovery process
 -proc:only           run annotation processors, but do not compile
 -proc:none           perform compilation but do not run annotation
 processors
 -s <dir>             destination directory for generated source files
 -XprintProcessorInfo print information about which annotations and elements
 a processor is asked to process
 -XprintRounds        print information about annotation processing rounds
 -classNames <className1[,className2,...]>
 qualified names of binary classes to process

 Advanced options:
 @<file>            read command line arguments from file
 -maxProblems <n>   max number of problems per compilation unit (100 by
 default)
 -log <file>        log to a file. If the file extension is '.xml', then
 the log will be a xml file.
 -proceedOnError[:Fatal]
 do not stop at first error, dumping class files with
 problem methods
 With ":Fatal", all optional errors are treated as fatal
 -verbose           enable verbose output
 -referenceInfo     compute reference info
 -progress          show progress (only in -log mode)
 -time              display speed information
 -noExit            do not call System.exit(n) at end of compilation (n==0
 if no error)
 -repeat <n>        repeat compilation process <n> times for perf analysis
 -inlineJSR         inline JSR bytecode (implicit if target >= 1.5)
 -enableJavadoc     consider references in javadoc
 -parameters        generate method parameters attribute (for target >= 1.8)
 -genericsignature  generate generic signature for lambda expressions
 -Xemacs            used to enable emacs-style output in the console.
 It does not affect the xml log output
 -missingNullDefault  report missing default nullness annotation
 -annotationpath <directories and ZIP archives separated by ;>
 specify locations where to find external annotations
 to support annotation-based null analysis.
 The special name CLASSPATH will cause lookup of
 external annotations from the classpath and sourcepath.

 -? -help           print this help message
 -v -version        print compiler version
 -showversion       print compiler version and continue

 Ignored options:
 -J<option>         pass option to virtual machine (ignored)
 -X<option>         specify non-standard option (ignored
 except for listed -X options)
 -X                 print non-standard options and exit (ignored)
 -O                 optimize for execution time (ignored)
 */
/**
 * @author zhang 本类封装了ECJ编译器
 */
public class ECJCompiler extends Compiler {

	/**
	 * 构建设置编译器输出路径的命令行
	 * 
	 * @return 设置编译器输出路径的命令行
	 */
	private CommandBuilder buildOutputPathCommand(CommandBuilder commandBuilder) {
		if (outputPath == null) {
			throw new RuntimeException("请指定编译输出目录");
		} else {
			commandBuilder.add("-d", outputPath);
		}
		return commandBuilder;
	}

	/**
	 * 构建设置编译水平的命令行
	 * 
	 * @return 返回设置编译水平的命令行文本
	 */
	private CommandBuilder buildCompileLevelCommand(CommandBuilder commandBuilder) {
		if (sourceLevel != null || targetLevel != null) {
			commandBuilder.add("-source", sourceLevel);
			commandBuilder.add("-target", targetLevel);
		}
		return commandBuilder;
	}

	private CommandBuilder buildClassPathCommand(CommandBuilder commandBuilder) {
		if (!dependencies.isEmpty()) {

			StringBuilder sb = new StringBuilder();
			for (Path path : dependencies) {
				sb.append(path.toString());
				sb.append(File.pathSeparator);
			}
			commandBuilder.add("-classpath", sb);
		}
		return commandBuilder;

	}

	private CommandBuilder buildSaveParmsNameCommand(CommandBuilder commandBuilder) {
		if (saveParmsName) {
			commandBuilder.add("-parameters", "-g");
		}
		return commandBuilder;
	}

	private CommandBuilder buildSourcePathCommand(CommandBuilder commandBuilder) {
		if (!sourcePath.isEmpty()) {
			commandBuilder.add(sourcePath);

		}
		return commandBuilder;
	}

	private CommandBuilder buildEncodingCommand(CommandBuilder commandBuilder) {
		commandBuilder.add("-encoding", encoding);
		return commandBuilder;
	}

	/**
	 * 构建完整的一次编译命令
	 * 
	 * @return 编译命令
	 */
	private CommandBuilder buildAllCommand(CommandBuilder commandBuilder) {
		buildClassPathCommand(commandBuilder);
		buildCompileLevelCommand(commandBuilder);
		buildSaveParmsNameCommand(commandBuilder);

		buildEncodingCommand(commandBuilder);
		// 构建输出路径
		buildOutputPathCommand(commandBuilder);

		// 构建源文件命令
		buildSourcePathCommand(commandBuilder);
		return commandBuilder;
	}

	/**
	 * 启动编译任务
	 */
	public void compile() {
		String command = buildAllCommand(new CommandBuilder()).toString();
		Logger.log("ECJ编译器编译命令 " + command);
		String[] argv = command.split(" ");
		PrintWriter printWriterOut = new PrintWriter(System.out);
		PrintWriter printWriterErr = new PrintWriter(System.err);

		new Main(printWriterOut, printWriterErr, false, null, null).compile(argv);
	}

}
