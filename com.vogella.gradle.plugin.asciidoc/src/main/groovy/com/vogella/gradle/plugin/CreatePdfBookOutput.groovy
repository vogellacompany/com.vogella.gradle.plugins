package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction;
import java.nio.file.FileSystems;

import groovy.lang.MetaClass
import java.nio.file.Path

class CreatePdfBookOutput extends AsciiDoc {

	public static final FOOTNOTETEXT = 'footnotetext'


	public CreatePdfBookOutput() {
		description = 'Creates PDF Book Documentation'
		group = 'Documentation'
		
		if (project.hasProperty(FOOTNOTETEXT)) {
			attributes FOOTNOTETEXT: project.property(FOOTNOTETEXT)
		}

		requires = ['asciidoctor-pdf'] + new CopyExtensions().extensions()
		backends = ['pdf']

		options doctype: 'book'
	}
	
	def matchesFilePattern(fileName) {
		fileName.startsWith('001_book')|| fileName.startsWith('001_script')
	}
}

