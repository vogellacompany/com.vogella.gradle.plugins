package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreatePdfOutput extends AsciiDoc {

	@Inject
	public CreatePdfOutput(WorkerExecutor we) {
		super(we)
		description = 'Creates PDF Documentation'
		group = 'Documentation'

		outputOptions {
			backends = ['pdf']
		}
	}
}