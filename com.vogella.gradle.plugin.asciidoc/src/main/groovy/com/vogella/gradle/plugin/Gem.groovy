package com.vogella.gradle.plugin

import java.util.Map

class Gem {
	def name, jrubyPrepare
	
	def getFolder() {
		"${jrubyPrepare.gemFolder}"
	}
	
	def getPath() {
		"${folder}/${fileName}"
	}
	
	def getFileName() {
		"${name}.gem"
	}
}
