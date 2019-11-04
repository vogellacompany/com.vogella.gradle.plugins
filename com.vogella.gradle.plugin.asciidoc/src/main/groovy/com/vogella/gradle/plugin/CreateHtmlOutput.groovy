package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreateHtmlOutput extends AsciiDoc {

	@Inject
	public CreateHtmlOutput(WorkerExecutor we) {
		super(we)
		description = 'Creates Html 5 Documentation'
		group = 'Documentation'
		attributes	'sectlinks':'true'
		
		outputOptions {
			backends = ['html5']
		}

		resources {
			from(sourceDir) { include 'img/**' }
		}
	}

   def matchesFilePattern(fileName) {
		fileName.startsWith("001_article")
   }
}
