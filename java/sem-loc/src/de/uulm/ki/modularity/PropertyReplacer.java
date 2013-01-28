/**
 * 
 */
package de.uulm.ki.modularity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * @author pavel
 *
 */
public class PropertyReplacer implements OWLAxiomVisitorEx<List<OWLAxiom>> {

	static final Logger LOGGER_ = Logger.getLogger(PropertyReplacer.class);
	
	private final Map<OWLObjectProperty, Collection<OWLObjectProperty>> propMap_;
	private final OWLDataFactory factory_ = OWLManager.getOWLDataFactory();
	private final ReplaceInCE ceReplacer_ = new ReplaceInCE();
	
	PropertyReplacer(Map<OWLObjectProperty, Collection<OWLObjectProperty>> propMap) {
		propMap_ = propMap;
	}
	
	@Override
	public List<OWLAxiom> visit(OWLSubAnnotationPropertyOfAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLAnnotationPropertyDomainAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLAnnotationPropertyRangeAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLSubClassOfAxiom arg0) {
		List<OWLClassExpression> sups = arg0.getSuperClass().accept(ceReplacer_);
		
		if (sups.size() == 1 && sups.get(0) == arg0.getSuperClass()) {
			return Collections.<OWLAxiom>singletonList(arg0);
		}
		else {
			List<OWLAxiom> axioms = new ArrayList<OWLAxiom>();
			
			for (OWLClassExpression sup : sups) {
				axioms.add(factory_.getOWLSubClassOfAxiom(arg0.getSubClass(), sup));
			}
			
			LOGGER_.trace("For " + arg0 + ", adding " + axioms);
			
			return axioms;
		}
	}

	@Override
	public List<OWLAxiom> visit(OWLNegativeObjectPropertyAssertionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLAsymmetricObjectPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLReflexiveObjectPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDisjointClassesAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDataPropertyDomainAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLObjectPropertyDomainAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLEquivalentObjectPropertiesAxiom arg0) {
		throw new RuntimeException(arg0.toString());
	}

	@Override
	public List<OWLAxiom> visit(OWLNegativeDataPropertyAssertionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDifferentIndividualsAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDisjointDataPropertiesAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDisjointObjectPropertiesAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLObjectPropertyRangeAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLObjectPropertyAssertionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLFunctionalObjectPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLSubObjectPropertyOfAxiom arg0) {
		throw new RuntimeException(arg0.toString());
	}

	@Override
	public List<OWLAxiom> visit(OWLDisjointUnionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);

	}

	@Override
	public List<OWLAxiom> visit(OWLDeclarationAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLAnnotationAssertionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLSymmetricObjectPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDataPropertyRangeAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLFunctionalDataPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLEquivalentDataPropertiesAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLClassAssertionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLEquivalentClassesAxiom arg0) {
		/*List<OWLClassExpression> classes = arg0.getClassExpressionsAsList();
		List<OWLAxiom> result = new ArrayList<OWLAxiom>();
		
		for (int i = 0; i < arg0.getClassExpressionsAsList().size() - 1; i++) {
			for (int j = i + 1; j < arg0.getClassExpressionsAsList().size(); j++) {
				if (!classes.get(i).isAnonymous() && classes.get(j).isAnonymous()) {
					result.addAll(factory_.getOWLSubClassOfAxiom(classes.get(i), classes.get(j)).accept(this));
				}
				
				if (!classes.get(j).isAnonymous() && classes.get(i).isAnonymous()) {
					result.addAll(factory_.getOWLSubClassOfAxiom(classes.get(j), classes.get(i)).accept(this));
				}
			}
		}
		
		if (result.isEmpty()) {
			return Collections.<OWLAxiom>singletonList(arg0);	
		}
		else {
			result.add(arg0);
			
			return result;
		}*/
		
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDataPropertyAssertionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLTransitiveObjectPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLIrreflexiveObjectPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLSubDataPropertyOfAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLInverseFunctionalObjectPropertyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLSameIndividualAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLSubPropertyChainOfAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLInverseObjectPropertiesAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLHasKeyAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(OWLDatatypeDefinitionAxiom arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	@Override
	public List<OWLAxiom> visit(SWRLRule arg0) {
		return Collections.<OWLAxiom>singletonList(arg0);
	}

	
	/**
	 * 
	 * @author pavel
	 *
	 */
	private class ReplaceInCE implements OWLClassExpressionVisitorEx<List<OWLClassExpression>> {

		@Override
		public List<OWLClassExpression> visit(OWLClass arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectIntersectionOf arg0) {
			Set<OWLClassExpression> ces = arg0.asConjunctSet();
			List<List<OWLClassExpression>> replacedCEs = new ArrayList<List<OWLClassExpression>>();
			boolean replaced = false;
			
			for (OWLClassExpression ce : ces) {
				List<OWLClassExpression> replacedCE = ce.accept(this);
				
				if (replacedCE.size() > 1 || replacedCE.get(0) != ce) {
					replaced = true;
				}
				
				replacedCEs.add(replacedCE);
			}
			
			if (!replaced) {
				return Collections.<OWLClassExpression>singletonList(arg0);
			}
			else {
				List<List<OWLClassExpression>> product = cartesianProduct(replacedCEs);
				List<OWLClassExpression> intersections = new ArrayList<OWLClassExpression>();
				 
				for (List<OWLClassExpression> intersection : product) {
					intersections.add(factory_.getOWLObjectIntersectionOf(intersection.toArray(new OWLClassExpression[]{})));
				}
				
				return intersections;
			}
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectUnionOf arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectComplementOf arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectSomeValuesFrom arg0) {			
			OWLObjectPropertyExpression prop = arg0.getProperty();
			Collection<OWLObjectProperty> supers = null;
			
			if (propMap_.containsKey(prop)) {
				supers = propMap_.get(prop);
			}
			
			List<OWLClassExpression> fillers = arg0.getFiller().accept(this);
			
			if (supers == null && fillers.size() == 1 && fillers.get(0) == arg0.getFiller()) {
				return Collections.<OWLClassExpression>singletonList(arg0);
			}
			else {
				List<OWLClassExpression> ces = new ArrayList<OWLClassExpression>();
				
				for (OWLObjectProperty sup : supers == null ? Collections.singleton(prop.asOWLObjectProperty()) : supers) {
					for (OWLClassExpression filler : fillers) {
						ces.add(factory_.getOWLObjectSomeValuesFrom(sup, filler));
					}
				}
				
				return ces;
			}
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectAllValuesFrom arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectHasValue arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectMinCardinality arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectExactCardinality arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectMaxCardinality arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectHasSelf arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLObjectOneOf arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLDataSomeValuesFrom arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLDataAllValuesFrom arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLDataHasValue arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLDataMinCardinality arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLDataExactCardinality arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}

		@Override
		public List<OWLClassExpression> visit(OWLDataMaxCardinality arg0) {
			return Collections.<OWLClassExpression>singletonList(arg0);
		}
		
		public List<List<OWLClassExpression>> cartesianProduct(List<List<OWLClassExpression>> lists) {
		    if (lists.size() < 1)
		        throw new IllegalArgumentException(lists.toString());
		    
		    if (lists.size() == 1) {
		    	return Collections.singletonList(lists.get(0));
		    }

		    return _cartesianProduct(0, lists);
		}

		private List<List<OWLClassExpression>> _cartesianProduct(int index, List<List<OWLClassExpression>> lists) {
		   List<List<OWLClassExpression>> ret = new ArrayList<List<OWLClassExpression>>();
		   
		    if (index == lists.size()) {
		        ret.add(new ArrayList<OWLClassExpression>());
		    } else {
		        for (OWLClassExpression ce : lists.get(index)) {
		            for (List<OWLClassExpression> list : _cartesianProduct(index+1, lists)) {
		                list.add(ce);
		                ret.add(list);
		            }
		        }
		    }
		    return ret;
		}		
	}
}
