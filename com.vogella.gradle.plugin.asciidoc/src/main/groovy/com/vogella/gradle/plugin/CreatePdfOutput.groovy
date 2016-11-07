package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction;
import com.github.jrubygradle.JRubyPrepare

import groovy.lang.MetaClass

class CreatePdfOutput extends AsciiDoc {

	public CreatePdfOutput() {
		description = 'Creates PDF Documentation'
		group = 'Documentation'

		requires = ['asciidoctor-pdf']
	    backends = ['pdf']
	}
}