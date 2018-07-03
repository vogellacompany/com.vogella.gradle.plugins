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

		def outputFolder = CopyExtensions.outputFolder(TaskUtil.topProject(project).buildDir)
		
		requires = ['asciidoctor-pdf', project.file("${outputFolder}/${CopyExtensions.PART_PAGES_EXTENSION_FILE}")]
		backends = ['pdf']

		options doctype: 'book'
	}
	
	def matchesFilePattern(fileName) {
		fileName.startsWith('001_book')|| fileName.startsWith('001_script')
	}
}

