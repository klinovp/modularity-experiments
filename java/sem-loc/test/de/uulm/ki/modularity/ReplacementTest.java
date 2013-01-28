package de.uulm.ki.modularity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class ReplacementTest {

	@Test
	public void testSimple() throws Exception {
		OWLOntology ontology = load("test1.owl");

		assertTrue(ReplaceByTopSuperrole.replace(ontology));
		//checks
		assertEquals(0, ontology.getAxiomCount(AxiomType.SUB_OBJECT_PROPERTY));
		assertEquals(8, ontology.getLogicalAxiomCount());
		ontology.getOWLOntologyManager().saveOntology(ontology, System.out);
	}
	
	private OWLOntology load(String resource) throws OWLOntologyCreationException, IOException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		InputStream stream = null;
		
		try {
			stream = getClass().getClassLoader().getResourceAsStream(resource);
			
			return manager.loadOntologyFromOntologyDocument(stream);
		}
		finally {
			stream.close();
		}
	}
}