package org.ravenbuild.plugins.dependencies;

import net.davidtanzer.jdefensive.Args;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public class Dependency {
	private final String artifactId;
	private final File locationOnDisk;
	private final Optional<URL> downloadURL;
	
	public Dependency(final String artifactId, final File locationOnDisk, final Optional<URL> downloadURL) {
		Args.notEmpty(artifactId, "artifactId");
		Args.notNull(locationOnDisk, "locationOnDisk");
		Args.notNull(downloadURL, "downloadURL");
		
		this.artifactId = artifactId;
		this.locationOnDisk = locationOnDisk;
		this.downloadURL = downloadURL;
	}
	
	public String artifactId() {
		return artifactId;
	}
	
	public File locationOnDisk() {
		return locationOnDisk;
	}
	
	public Optional<URL> downloadURL() {
		return downloadURL;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Dependency that = (Dependency) o;
		
		if (!artifactId.equals(that.artifactId)) {
			return false;
		}
		if (!locationOnDisk.equals(that.locationOnDisk)) {
			return false;
		}
		return downloadURL.equals(that.downloadURL);
		
	}
	
	@Override
	public int hashCode() {
		int result = artifactId.hashCode();
		result = 31 * result + locationOnDisk.hashCode();
		result = 31 * result + downloadURL.hashCode();
		return result;
	}
}
