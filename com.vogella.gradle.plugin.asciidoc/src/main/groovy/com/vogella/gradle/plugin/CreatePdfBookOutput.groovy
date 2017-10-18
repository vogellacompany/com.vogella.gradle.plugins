package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction;

import groovy.lang.MetaClass

class CreatePdfBookOutput extends AsciiDoc {
	
	public static final FOOTNOTETEXT = 'footnotetext'

	public CreatePdfBookOutput() {
		description = 'Creates PDF Book Documentation'
		group = 'Documentation'
		
		if (project.hasProperty(FOOTNOTETEXT)) {
			attributes FOOTNOTETEXT: project.property(FOOTNOTETEXT)
		}

		requires = ['asciidoctor-pdf']
		backends = ['pdf']

		options doctype: 'book'
	}


	def matchesFilePattern(fileName) {
		fileName.startsWith('001_book')|| fileName.startsWith('001_script')
	}
}

