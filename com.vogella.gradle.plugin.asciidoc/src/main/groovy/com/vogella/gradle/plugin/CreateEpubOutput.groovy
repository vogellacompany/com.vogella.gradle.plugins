package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreateEpubOutput extends AsciiDoc {

	@Inject
	public CreateEpubOutput(WorkerExecutor we) {
		super(we)
		description = 'Creates Epub 3 Documentation'
		group = 'Documentation'

		outputOptions {
			backends = ['epub3']
		}
	}
}
