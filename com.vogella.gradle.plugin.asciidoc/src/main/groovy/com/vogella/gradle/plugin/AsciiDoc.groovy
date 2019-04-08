package com.vogella.gradle.plugin

import org.asciidoctor.gradle.base.SafeMode
import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor
import org.gradle.api.logging.StandardOutputListener

@CacheableTask
class AsciiDoc extends AsciidoctorTask {
	
    static final ERROR_MESSAGES = [
								   'include file not found',
		                           'only book doctypes can contain level 0 sections',
		                           'section title out of sequence',
		                           'dropping line containing reference to missing attribute',
		                           'could not embed image',
								   'image to embed not found or not readable',
								   'could not resolve xref',
								   'already in use'
		                          ]

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

		sourceDir = project.file("${project.projectDir}")
		def sourceFiles = new File("${sourceDir}").listFiles()
									   .collect { it.name }
									   .findAll { matchesFilePattern(it)}
		sources { setIncludes sourceFiles }
		outputDir project.buildDir
		outputOptions {
			setSeparateOutputDirs(true)
		}

		options doctype: 'article',
		template_dirs : [ new File(TaskUtil.topProject(project).projectDir, '_templates').absolutePath ]

		attributes	'source-highlighter' : 'coderay',
		'icons': 'font',
		'setanchors':'true',
		'tabsize': '4',
		'idprefix':'',
		'idseparator':'-',
		'docinfo1':'true',
		'pdf-stylesdir':'../themes',
		'pdf-style':'basic'
		
		def capturedOutput = []
		def listener = { capturedOutput << it } as StandardOutputListener
		logging.addStandardOutputListener(listener)
		logging.addStandardErrorListener(listener)
		doLast {
			logging.removeStandardOutputListener(listener)
			logging.removeStandardErrorListener(listener)
			checkForRelevantWarnings(capturedOutput)
		}
	}

	@TaskAction
	def renameFiles() {
		renameFiles(new File("${project.buildDir}"))
	}
	
	def matchesFilePattern(fileName) {
		fileName.startsWith("001_article") || fileName.startsWith('001_book') || fileName.contains('exercise_') || fileName.startsWith('001_script')
	}

	def renameFiles(def buildDir) {
		buildDir.eachFileRecurse( {
			if(it.name.startsWith('001_')){
				it.renameTo(new File(it.parent, it.name.substring(4, it.name.length())))
			}
		}
		)
	}
	
	def checkForRelevantWarnings(outputEvents) {
		outputEvents.each { e ->
			if (matchesRelevantWarning(e.toString())) {
				throw new GradleException("You have some asciidoctor warnings, please fix them!");
			}
		}
	}
	
	def matchesRelevantWarning(e) {
		ERROR_MESSAGES.any { e =~ it }
	}
}

