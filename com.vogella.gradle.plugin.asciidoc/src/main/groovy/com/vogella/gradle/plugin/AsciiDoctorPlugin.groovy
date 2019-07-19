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
			extensions.getByType(AsciidoctorJExtension).getModules().getEpub().setVersion(AsciidoctorJExtension.DEFAULT_EPUB_VERSION)
			extensions.getByType(AsciidoctorJExtension).getModules().getPdf().setVersion(AsciidoctorJExtension.DEFAULT_PDF_VERSION)
			
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
			task('renameFiles', type: RenameFiles)

			// only apply asciidoctor tasks to child projects
			childProjects.each { k, v ->
				v.task('createAll', type: CreateAllOutputFormats).finalizedBy('renameFiles')
				v.task('createPdf', type: CreatePdfOutput).finalizedBy('renameFiles')
				v.task('createPdfBook', type: CreatePdfBookOutput, dependsOn: 'copyExtensions').finalizedBy('renameFiles')
				v.task('createEpub', type: CreateEpubOutput).finalizedBy('renameFiles')
				v.task('createDocbook', type: CreateDocbookOutput).finalizedBy('renameFiles')
				v.task('createHtml', type: CreateHtmlOutput).finalizedBy('renameFiles')
				v.task('createExerciseTestHtml', type: CreateHtmlExerciseTestOutput).finalizedBy('renameFiles')
				v.task('createExerciseHtml', type: CreateHtmlExerciseOutput).finalizedBy('renameFiles')
				v.task('publishHtml', type: PublishHtmlOutput)
			}
		}
	}	
}
