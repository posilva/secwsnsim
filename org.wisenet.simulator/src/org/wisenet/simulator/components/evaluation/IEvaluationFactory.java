/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.evaluation;

/**
 *
 * @author CIAdmin
 */
public interface IEvaluationFactory {

    public IEvaluationDeploy create(EvaluationDeployModeEnum mode, int param, int w, int h);

     public IEvaluationTest create(EvaluationTestTypeEnum type, EvaluationTestSettings  settings) ;

    public IEvaluationAttack create(EvaluationAttackTypeEnum type, int numAttackNodes);
}
