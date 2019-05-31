package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreateHtmlExerciseOutput extends CreateHtmlExerciseTestOutput {

	@Inject
	public CreateHtmlExerciseOutput(WorkerExecutor we) {
		super(we)
		description = 'Creates Html 5 Exercises for learn platform'
		group = 'Documentation'

		options template_dirs: [ new File(TaskUtil.topProject(project).projectDir, '_templates').absolutePath ]
	}
}
