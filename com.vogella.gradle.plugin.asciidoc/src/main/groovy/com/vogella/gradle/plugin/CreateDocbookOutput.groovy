package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreateDocbookOutput extends AsciiDoc {

	@Inject
	public CreateDocbookOutput(WorkerExecutor we) {
		super(we)
		description = 'Creates Docbook Documentation'
		group = 'Documentation'

		outputOptions {
			backends = ['docbook45']
		}
		options doctype: 'article'
	}
}
