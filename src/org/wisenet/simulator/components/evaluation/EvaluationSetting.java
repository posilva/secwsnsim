/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.evaluation;

/**
 *
 * @author CIAdmin
 */
public class EvaluationSetting {

    IEvaluationAttack evaluationAttack;
    IEvaluationDeploy evaluationDeploy;
    IEvaluationTest evaluationTest;

    IEvaluationFactory factory;

    public IEvaluationFactory getFactory() {
        return factory;
    }

    public void setFactory(IEvaluationFactory factory) {
        this.factory = factory;
    }

}
