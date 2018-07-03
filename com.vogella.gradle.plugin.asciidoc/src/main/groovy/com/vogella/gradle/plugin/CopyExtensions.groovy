package com.vogella.gradle.plugin

import java.util.Map
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CopyExtensions extends DefaultTask {
	
	static final String EXTENSIONS_OUTPUT_FOLDER = 'extensions'

	static final String PART_PAGES_EXTENSION_FILE = 'styling_for_part_pages_extension.rb'
	static final List EXTENSIONS = [PART_PAGES_EXTENSION_FILE]

	def static outputFolder(buildDir) {
		"${buildDir}/${EXTENSIONS_OUTPUT_FOLDER}"
	}
	
	@TaskAction
	def copyExtensions() {
		def outputFolder = outputFolder(TaskUtil.topProject(project).buildDir)
		EXTENSIONS.each {
			InputStream src = getClass().getResourceAsStream(it)
			project.file(outputFolder).mkdirs()
			def destFile = project.file("${outputFolder}/${it}")
			if (src) {
				println AsciiDoctorPlugin.LOG_PREFIX + "copying ${it} to ${destFile}"
				def dest = destFile.newDataOutputStream()
				dest << src
				src.close()
				dest.close()
			} else {
				throw new FileNotFoundException("Copy failed. Couldn't find extension: ${it}")
			}
		}
	}
}
