package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreateHtmlExerciseTestOutput extends CreateHtmlOutput {

	@Inject
	public CreateHtmlExerciseTestOutput(WorkerExecutor we) {
		super(we)
		description = 'Creates Html 5 Exercises for learn platform without template'
		group = 'Documentation'
	}

	def matchesFilePattern(fileName) {
		fileName.contains('exercise_') || fileName.contains('content_')
   }
}
