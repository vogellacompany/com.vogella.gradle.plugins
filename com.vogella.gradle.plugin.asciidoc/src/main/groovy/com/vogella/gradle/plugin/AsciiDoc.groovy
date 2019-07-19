package com.vogella.gradle.plugin

import org.asciidoctor.gradle.base.SafeMode
import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.StopActionException
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.util.PatternSet
import org.gradle.workers.WorkerExecutor
import org.gradle.api.logging.StandardOutputListener

@CacheableTask
class AsciiDoc extends AsciidoctorTask {

	public AsciiDoc(WorkerExecutor we) {
		super(we)

		inProcess JAVA_EXEC
		forkOptions {
			setMaxHeapSize '4096m'
		}

		// allow asciidoc includes from outside the parent folder
		asciidoctorj {
			safeMode SafeMode.UNSAFE
		}

		def srcDir = project.projectDir
		def sourceFiles = srcDir.listFiles()
					    .collect { it.name }
						.findAll { matchesFilePattern(it)}

//		 if sourceFiles is empty, all possible adoc files become source files / no filtering happens
//       => only set sourceDir when we find matching sourceFiles
		if (!sourceFiles.isEmpty()) {
		    sourceDir = srcDir
	        sources { setIncludes sourceFiles }
		}
		outputDir project.buildDir
		outputOptions {
			setSeparateOutputDirs(true)
		}

		options doctype: 'article'

		attributes	'source-highlighter' : 'rouge',
		'rouge-style': 'thankful_eyes',
		'icons': 'font',
		'setanchors':'true',
		'tabsize': '4',
		'idprefix':'',
		'idseparator':'-',
		'docinfo1':'true',
		'pdf-stylesdir':'../themes',
		'pdf-style':'basic'
	}
	
	def matchesFilePattern(fileName) {
		fileName.startsWith("001_article") || fileName.startsWith('001_book')  || fileName.startsWith('001_script')
	}

}

