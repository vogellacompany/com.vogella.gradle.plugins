package com.vogella.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.asciidoctor.gradle.jvm.AsciidoctorJBasePlugin
import org.asciidoctor.gradle.jvm.AsciidoctorJExtension

class AsciiDoctorPlugin implements Plugin<Project> {
	
	public static final LOG_PREFIX = 'Vogella Asciidoctor: '
	static final ERROR_MESSAGES = [
		'include file not found',
		'only book doctypes can contain level 0 sections',
		'section title out of sequence',
		'dropping line containing reference to missing attribute',
		'could not embed image',
		'image to embed not found or not readable',
		'could not resolve xref',
		'already in use',
		'unterminated listing block',
		'invalid style for listing block'
	   ]

	void apply(Project target) {
		target.with { 
			apply plugin : 'org.asciidoctor.jvm.base'
			apply plugin : 'org.asciidoctor.jvm.pdf'
			apply plugin : 'org.asciidoctor.jvm.epub'
			extensions.getByType(AsciidoctorJExtension).epubVersion = AsciidoctorJExtension.DEFAULT_EPUB_VERSION
			extensions.getByType(AsciidoctorJExtension).pdfVersion = AsciidoctorJExtension.DEFAULT_PDF_VERSION
			
			repositories {
				jcenter()
				mavenCentral()
			}

			asciidoctorj {
				version = '2.0.0'
				setFatalWarnings(ERROR_MESSAGES)
			}

			task('copyExtensions', type: CopyExtensions)
			task('createTemplate', type: Template)

			// only apply asciidoctor tasks to child projects
			childProjects.each { k, v ->
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
}
