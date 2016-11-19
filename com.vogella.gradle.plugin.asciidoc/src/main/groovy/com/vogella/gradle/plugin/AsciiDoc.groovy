package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.gradle.api.logging.StandardOutputListener

class AsciiDoc extends AsciidoctorTask {

	public AsciiDoc() {
		sourceDir = project.file("${project.projectDir}")
		def sourceFiles = new File("${sourceDir}").listFiles()
									   .collect { it.name }
									   .findAll { matchesFilePattern(it)}
		sources { setIncludes sourceFiles }
		outputDir project.buildDir
		gemPath = project.jrubyPrepareDev.outputDir

		options doctype: 'article'

		attributes	'source-highlighter' : 'coderay',
		'toc':'',
		'icons': 'font',
		'setanchors':'true',
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
		fileName.startsWith("001_article") || fileName.startsWith('001_book')
	}

	def renameFiles(def buildDir) {
		buildDir.eachFileRecurse( {
			if(matchesFilePattern(it.name)){
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
		e =~ "include file not found" || e =~ "only book doctypes can contain level 0 sections" || e =~ "section title out of sequence"
	}
}

