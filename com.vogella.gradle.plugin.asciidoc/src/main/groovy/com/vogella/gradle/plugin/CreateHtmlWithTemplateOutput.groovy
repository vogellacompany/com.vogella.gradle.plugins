package com.vogella.gradle.plugin

import javax.inject.Inject

import org.gradle.workers.WorkerExecutor

import groovy.lang.MetaClass

class CreateHtmlWithTemplateOutput extends CreateHtmlOutput {

	@Inject
	public CreateHtmlWithTemplateOutput(WorkerExecutor we) {
		super(we)
		options template_dirs: [ new File(TaskUtil.topProject(project).projectDir, '_template_vogellacom').absolutePath ]
	}
}
