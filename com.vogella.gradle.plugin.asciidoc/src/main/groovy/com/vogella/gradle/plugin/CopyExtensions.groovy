package com.vogella.gradle.plugin

import java.util.Map
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.util.stream.Collectors

class CopyExtensions extends DefaultTask {
	
	static final String EXTENSIONS_OUTPUT_FOLDER = 'extensions'

	static final String PART_PAGES_EXTENSION_FILE = 'styling_for_part_pages_extension.rb'
	static final List EXTENSIONS = [PART_PAGES_EXTENSION_FILE]
	
	public CopyExtensions() {		
		doLast {
			copyExtensions(outputFolder())
		}
	}
	
	def extensions() {
		EXTENSIONS.stream().map({ "${outputFolder()}${File.separator}${it}" }).collect(Collectors.toList())
	}
	
	def outputFolder() {
		def projectDir = TaskUtil.topProject(project).projectDir
		"${projectDir}${File.separator}build${File.separator}${EXTENSIONS_OUTPUT_FOLDER}"
	}
	
	def copyExtensions(outputFolder) {
		project.file(outputFolder).mkdirs()
		EXTENSIONS.each {
			InputStream src = getClass().getResourceAsStream(it)
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
