package com.vogella.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin

class AsciiDoctorPlugin implements Plugin<Project> {
	
	public static final LOG_PREFIX = 'Vogella Asciidoctor: '

	void apply(Project target) {
		target.apply([plugin:('org.asciidoctor.convert')])
		target.asciidoctorj {
			version = '1.5.7'
		}
		target.task('copyExtensions', type: CopyExtensions)
		target.task('createTemplate', type: Template)

		// only apply asciidoctor tasks to child projects
		target.childProjects.each { k, v ->
			v.task('createAll', type: CreateAllOutputFormats)
			v.task('createPdf', type: CreatePdfOutput)
			v.task('createPdfBook', type: CreatePdfBookOutput, dependsOn: 'copyExtensions')
			v.task('createEpub', type: CreateEpubOutput)
			v.task('createDocbook', type: CreateDocbookOutput)
			v.task('createHtml', type: CreateHtmlOutput)
			v.task('publishHtml', type: PublishHtmlOutput)
		}
				
	}
}
