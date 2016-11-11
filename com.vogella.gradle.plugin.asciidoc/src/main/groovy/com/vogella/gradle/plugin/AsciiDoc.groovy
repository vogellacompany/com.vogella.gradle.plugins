package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction

class AsciiDoc extends AsciidoctorTask {

	public AsciiDoc() {
		sourceDir = project.file("${project.projectDir}")
		def sourceFiles = new File('.').listFiles()
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
	}

	@TaskAction
	def renameFile() {
		renameFile(new File("${project.buildDir}"))
	}
	
	def matchesFilePattern(fileName) {
		fileName.startsWith("001_article") || fileName.startsWith('001_book')
	}

	def renameFile(def buildDir) {
		buildDir.eachFileRecurse( {
			if(matchesFilePattern(it.name)){
				it.renameTo(new File(it.parent, it.name.substring(4, it.name.length())))
			}
		}
		)
	}
}

