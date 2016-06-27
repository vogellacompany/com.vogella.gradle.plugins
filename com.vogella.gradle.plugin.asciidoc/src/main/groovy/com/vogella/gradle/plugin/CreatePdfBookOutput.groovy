package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction;

import groovy.lang.MetaClass

class CreatePdfBookOutput extends AsciiDoc {

	public CreatePdfBookOutput() {
		description = 'Creates PDF Book Documentation'
		group = 'Documentation'

		backends = ['pdf']

		options doctype: 'book'
	}
}