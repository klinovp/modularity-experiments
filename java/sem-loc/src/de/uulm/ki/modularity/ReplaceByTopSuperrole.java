/**
 * 
 */
package de.uulm.ki.modularity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import de.uulm.ecs.ai.owlapi.krssparser.KRSS2OntologyFormat;

/**
 * @author pavel
 * 
 */
public class ReplaceByTopSuperrole {

	static final Logger LOGGER_ = Logger.getLogger(ReplaceByTopSuperrole.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		for (Iterator<OWLOntology> iter = new AllOntologiesIterator("/home/pavel/misc/java/sem-loc/data/ontos"); iter.hasNext();) {
			OWLOntology ontology = iter.next();

			if (ontology != null) {
				LOGGER_.trace(ontology.getOWLOntologyManager().getOntologyDocumentIRI(ontology));

				boolean replaced = false;
				
				try {
					replaced = replace(ontology);
				} catch (RuntimeException e) {
					LOGGER_.error("Error processing " + ontology.getOntologyID(), e);
					continue;
				}

				try {
					if (replaced) {
						save(ontology);
					}
				} catch (OWLOntologyStorageException e) {
					LOGGER_.error("Error saving " + ontology.getOntologyID(), e);
				}
			}
		}
	}

	static boolean replace(OWLOntology ontology) {
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		// first, map each object property to its top super-property
		Map<OWLObjectProperty, Collection<OWLObjectProperty>> propMap = getPropertyMap(ontology);

		if (!propMap.isEmpty()) {
			
			LOGGER_.trace("Property map: " + propMap);
			
			PropertyReplacer replacer = new PropertyReplacer(propMap);
			Set<OWLAxiom> added = new HashSet<OWLAxiom>();
			// now replace property occurrences
			for (OWLAxiom axiom : ontology.getLogicalAxioms()) {
				List<OWLAxiom> processed = axiom.accept(replacer);

				if (processed.size() > 1 || processed.get(0) != axiom) {
					added.addAll(processed);
				}
			}

			if (!added.isEmpty() || !propMap.isEmpty()) {
				manager.addAxioms(ontology, added);

				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	private static Map<OWLObjectProperty, Collection<OWLObjectProperty>> getPropertyMap(OWLOntology ontology) {
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		Map<OWLObjectProperty, Set<OWLObjectProperty>> subPropMap = new HashMap<OWLObjectProperty, Set<OWLObjectProperty>>();
		
		  
		for (OWLAxiom axiom : ontology.getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
			LOGGER_.error("Equivalent properties (" + axiom + ") in " + ontology.getOntologyID());
		}
		 
		// now subproperty axioms
		for (OWLAxiom axiom : ontology.getAxioms(AxiomType.SUB_OBJECT_PROPERTY)) {
			OWLSubObjectPropertyOfAxiom ax = (OWLSubObjectPropertyOfAxiom) axiom;

			if (!ax.getSubProperty().isAnonymous() 	&& !ax.getSuperProperty().isAnonymous()
					&& !ax.getSuperProperty().isOWLTopObjectProperty() && !ax.getSubProperty().isOWLBottomObjectProperty()) {
				Set<OWLObjectProperty> supers = subPropMap.get(ax.getSubProperty().asOWLObjectProperty());

				if (supers == null) {
					supers = new HashSet<OWLObjectProperty>();
				}
				
				if (!supers.contains(ax.getSubProperty())) {
					supers.add(ax.getSuperProperty().asOWLObjectProperty());
					subPropMap.put(ax.getSubProperty().asOWLObjectProperty(), supers);
				}
			}

			manager.removeAxiom(ontology, ax);
		}

		Map<OWLObjectProperty, Collection<OWLObjectProperty>> propMap = new HashMap<OWLObjectProperty, Collection<OWLObjectProperty>>();
		// now get the top-most super-properties
		for (OWLObjectProperty prop : subPropMap.keySet()) {
			List<OWLObjectProperty> supers = new ArrayList<OWLObjectProperty>(subPropMap.get(prop));
			boolean fixedpoint = true;

			do {
				fixedpoint = true;
				int size = supers.size();

				for (int i = 0; i < size; i++) {
					OWLObjectProperty sup = supers.remove(0);
					// replace sup by all its super-properties
					if (subPropMap.containsKey(sup)) {
						Set<OWLObjectProperty> directSupers = subPropMap.get(sup);
						//TODO detect property cycles here
						/*if (directSupers.contains(sup)) {
							LOGGER_.error("cyclic properties (" + sup + ": " + directSupers + ") in " + ontology.getOWLOntologyManager().getOntologyDocumentIRI(ontology));
							// breaking the loop arbitrarily
							supers.remove(sup);
						}*/
						
						if (supers.addAll(directSupers)) {
							fixedpoint = false;
						}

						
					} else {
						supers.add(sup);
					}
				}
			} while (!fixedpoint);

			propMap.put(prop, supers);
		}

		return propMap;
	}

	private static void save(OWLOntology ontology) throws OWLOntologyStorageException {
		// save in the same dir
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		IRI iri = manager.getOntologyDocumentIRI(ontology);

		if (iri != null) {
			String path = iri.toString();
			int index = path.lastIndexOf('/');

			path = path.substring(0, index) + "/property_hierarchy_approximated_"	+ path.substring(index + 1);

			manager.saveOntology(ontology, new KRSS2OntologyFormat(), IRI.create(path));
		}
	}
}