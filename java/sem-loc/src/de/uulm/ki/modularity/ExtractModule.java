package de.uulm.ki.modularity;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

public class ExtractModule {

	static final OWLDataFactory FACTORY = OWLManager.getOWLDataFactory();
	static final OWLEntity[] TERMS = new OWLEntity[] {
			FACTORY.getOWLClass(IRI
					.create("http://www.co-ode.org/ontologies/galen#RightSideOfHeart")),
			FACTORY.getOWLClass(IRI
					.create("http://www.co-ode.org/ontologies/galen#RightIneffectiveCardiacFunction")),
			FACTORY.getOWLObjectProperty(IRI
					.create("http://www.co-ode.org/ontologies/galen#isSpecificFunctionOf")),
			FACTORY.getOWLClass(IRI
					.create("http://www.co-ode.org/ontologies/galen#IneffectiveCardiacFunction")) };

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();	
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File("/home/pavel/misc/sem-loc/AF_galen.krss"));
		SyntacticLocalityModuleExtractor extractor = new SyntacticLocalityModuleExtractor(manager, ontology, ModuleType.STAR);
		
		Set<OWLAxiom> module = extractor.extract(new HashSet<OWLEntity>(Arrays.asList(TERMS)));
		
		/*for (OWLAxiom axiom : module) {
			System.out.println(axiom);
		}
		
		System.out.println(module.size());*/
	}
}
