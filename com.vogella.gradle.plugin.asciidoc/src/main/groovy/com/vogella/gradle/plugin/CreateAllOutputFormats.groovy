package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreateAllOutputFormats extends AsciiDoc {

	@Inject
	public CreateAllOutputFormats(WorkerExecutor we) {
		super(we)
		description = 'Creates Html 5, PDF and Epub 3 Documentation'
		group = 'Documentation'

		outputOptions {
			backends = ['html5', 'pdf', 'epub3']
		}

		resources {
			from(sourceDir) { include 'img/**' }
		}
	}
}
