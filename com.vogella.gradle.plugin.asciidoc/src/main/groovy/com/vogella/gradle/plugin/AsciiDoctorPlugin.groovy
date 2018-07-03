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
        target.task('createAll', type: CreateAllOutputFormats)
        target.task('createPdf', type: CreatePdfOutput)
        target.task('createPdfBook', type: CreatePdfBookOutput, dependsOn: 'copyExtensions')
        target.task('createEpub', type: CreateEpubOutput)
        target.task('createDocbook', type: CreateDocbookOutput)
        target.task('createHtml', type: CreateHtmlOutput)
        target.task('publishHtml', type: PublishHtmlOutput)
    }
}
