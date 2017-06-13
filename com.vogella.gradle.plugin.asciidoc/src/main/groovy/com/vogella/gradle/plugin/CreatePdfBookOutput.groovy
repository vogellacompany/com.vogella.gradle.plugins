package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction;

import groovy.lang.MetaClass

class CreatePdfBookOutput extends AsciiDoc {

	public CreatePdfBookOutput() {
		description = 'Creates PDF Book Documentation'
		group = 'Documentation'
		def participantName = project.hasProperty('participantName') ? project.property('participantName') : ''
		attributes 'participantName' : participantName

		requires = ['asciidoctor-pdf']
		backends = ['pdf']

		options doctype: 'book'
	}
}

