package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction;

import groovy.lang.MetaClass

class CreateDocbookOutput extends AsciiDoc {

	public CreateDocbookOutput() {
		description = 'Creates Docbook Documentation'
		group = 'Documentation'

		backends = ['docbook45']
	}
}