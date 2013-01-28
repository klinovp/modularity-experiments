/**
 * 
 */
package de.uulm.ki.modularity;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author pavel
 *
 */
public class AllOntologiesIterator implements Iterator<OWLOntology> {

	private final OWLOntologyManager manager_ = OWLManager.createOWLOntologyManager();
	private File nextFile_ = null;
	private int dirIndex_ = -1;
	private final File[] ontoDirs_;
	private File[] dirFiles_;
	private int dirFileIndex_;
	
	AllOntologiesIterator(String srcPath) {
		File srcDir = new File(srcPath);
		
		if (!srcDir.exists() && !srcDir.isDirectory()) {
			throw new RuntimeException("Invalid source dir");
		}
		
		ontoDirs_ = srcDir.listFiles();
		
	}

	@Override
	public boolean hasNext() {
		if (nextFile_ == null) {
			// see if there's next file in the current dir
			if (dirFiles_ != null) {
				if (++dirFileIndex_ < dirFiles_.length) {
					nextFile_ = dirFiles_[dirFileIndex_];
				}
				else {
					// no next file in the current dir
					dirFiles_ = null;
				}
			}
			
			if (dirFiles_ == null) {
				dirFileIndex_ = 0;
				// move to the next dir
				if (++dirIndex_ < ontoDirs_.length) {
					dirFiles_ = ontoDirs_[dirIndex_].listFiles();
					nextFile_ = dirFiles_[dirFileIndex_];
				}
			}
		}
		
		return nextFile_ != null;
	}

	@Override
	public OWLOntology next() {
		if (nextFile_ != null) {
			return load();
		}
		else if (!hasNext()) {
			throw new NoSuchElementException();
		}
		else {
			return load();
		}
	}

	private OWLOntology load() {
		File nextFile = nextFile_;
		
		try {
			nextFile_ = null;
			
			return manager_.loadOntologyFromOntologyDocument(nextFile);
		} catch (OWLOntologyCreationException e) {
			System.err.println("Ontology " + nextFile + " can't be loaded");
			
			return null;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}