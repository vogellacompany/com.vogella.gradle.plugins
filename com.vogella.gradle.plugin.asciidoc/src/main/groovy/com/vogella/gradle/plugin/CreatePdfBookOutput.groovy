package com.vogella.gradle.plugin

import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkerExecutor

import java.nio.file.FileSystems;

import groovy.lang.MetaClass
import java.nio.file.Path

import javax.inject.Inject

class CreatePdfBookOutput extends AsciiDoc {

	public static final FOOTNOTETEXT = 'footnotetext'

	@Inject
	public CreatePdfBookOutput(WorkerExecutor we) {
		super(we)
		description = 'Creates PDF Book Documentation'
		group = 'Documentation'
		
		if (project.hasProperty(FOOTNOTETEXT)) {
			attributes FOOTNOTETEXT: project.property(FOOTNOTETEXT)
		}

		outputOptions {
			backends = ['pdf']
		}

		options doctype: 'book'
	}
	
}

