package com.vogella.gradle.plugin

import org.gradle.api.tasks.CacheableTask
import org.gradle.workers.WorkerExecutor

@CacheableTask
class AsciiDocPdf extends AsciiDoc {
	
	public AsciiDocPdf(WorkerExecutor we) {
		super(we)
	}

	def matchesFilePattern(fileName) {
		fileName.startsWith("001_article") || fileName.startsWith('001_book') || fileName.startsWith('001_script')
	}

}

