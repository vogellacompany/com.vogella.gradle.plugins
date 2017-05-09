package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.GradleException
import org.gradle.api.logging.StandardOutputListener
import org.gradle.api.tasks.TaskAction

class AsciiDoc extends AsciidoctorTask {

	public AsciiDoc() {
		sourceDir = project.file("${project.projectDir}")
		sources { include '001_article.adoc' }
		outputDir "${project.buildDir}/"

		options doctype: 'article'

		attributes	'source-highlighter' : 'coderay',
		'toc':'',
		'icons': 'font',
		'setanchors':'true',
		'sectnums': '',
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
	def renameFile() {
		renameFile(new File("${project.buildDir}"))
	}

	def renameFile(def buildDir) {
		buildDir.eachFileRecurse( {
			if(it.name.startsWith("001_article")){
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
		e =~ "include file not found" || e =~ "only book doctypes can contain level 0 sections" || e =~ "section title out of sequence" || e =~ "dropping line containing reference to missing attribute"
	}
}

