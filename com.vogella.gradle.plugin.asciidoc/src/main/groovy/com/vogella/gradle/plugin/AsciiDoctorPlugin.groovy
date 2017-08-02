package com.vogella.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin
import com.github.jrubygradle.JRubyPrepare

class AsciiDoctorPlugin implements Plugin<Project> {
	
	public static final LOG_PREFIX = 'Vogella Asciidoctor: '

    void apply(Project target) {
		target.apply([plugin:('org.asciidoctor.convert')])
		target.apply([plugin:('com.github.jruby-gradle.base')])
		target.dependencies {
		  gems 'rubygems:prawn:2.2.0'
		  gems 'rubygems:prawn-table:0.2.2'
		  gems 'rubygems:prawn-templates:0.0.4'
		  gems 'rubygems:prawn-svg:0.27.0'
		  gems 'rubygems:prawn-icon:1.3.0'
		  gems 'rubygems:safe_yaml:1.0.4'
		  gems 'rubygems:thread_safe:0.3.6'
		  gems 'rubygems:treetop:1.5.3'
		  gems 'rubygems:asciidoctor:1.5.6.1'
		}
		target.asciidoctorj {
            version = '1.5.6'
        }
		target.task('jrubyPrepareDev', type: JRubyPrepareDev)
        target.task('createTemplate', type: Template)
        target.task('createAll', type: CreateAllOutputFormats, dependsOn: 'jrubyPrepareDev')
        target.task('createPdf', type: CreatePdfOutput, dependsOn: 'jrubyPrepareDev')
        target.task('createPdfBook', type: CreatePdfBookOutput, dependsOn: 'jrubyPrepareDev')
        target.task('createEpub', type: CreateEpubOutput, dependsOn: 'jrubyPrepareDev')
        target.task('createDocbook', type: CreateDocbookOutput, dependsOn: 'jrubyPrepareDev')
        target.task('createHtml', type: CreateHtmlOutput, dependsOn: 'jrubyPrepareDev')
        target.task('publishHtml', type: PublishHtmlOutput)
    }
}
