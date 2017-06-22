package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction;

import groovy.lang.MetaClass

class CreatePdfBookOutput extends AsciiDoc {
	
	public static final FOOTNOTETEXT = 'footnotetext'

	public CreatePdfBookOutput() {
		description = 'Creates PDF Book Documentation'
		group = 'Documentation'
		def participantName = project.hasProperty(FOOTNOTETEXT) ? project.property(FOOTNOTETEXT) : ''
		attributes FOOTNOTETEXT: participantName

		requires = ['asciidoctor-pdf']
		backends = ['pdf']

		options doctype: 'book'
	}
}

