/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.platform.evaluation;

import org.wisenet.simulator.components.evaluation.EvaluationAttackTypeEnum;
import org.wisenet.simulator.components.evaluation.EvaluationDeployModeEnum;
import org.wisenet.simulator.components.evaluation.EvaluationTestSettings;
import org.wisenet.simulator.components.evaluation.EvaluationTestTypeEnum;
import org.wisenet.simulator.components.evaluation.IEvaluationAttack;
import org.wisenet.simulator.components.evaluation.IEvaluationDeploy;
import org.wisenet.simulator.components.evaluation.IEvaluationFactory;
import org.wisenet.simulator.components.evaluation.IEvaluationTest;

/**
 *
 * @author CIAdmin
 */
public class PlatformEvaluationFactory implements IEvaluationFactory {

    public static PlatformEvaluationFactory instance;

    public PlatformEvaluationFactory() {
        if (instance != null) {
            throw new IllegalStateException("PlatformEvaluationFactory is a singleton class");
        }

    }

    public static PlatformEvaluationFactory getInstance() {
        if (instance == null) {
            instance = new PlatformEvaluationFactory();
        }
        return instance;
    }

    public IEvaluationTest create(EvaluationTestTypeEnum type, EvaluationTestSettings settings) {
        return new PlatformEvaluationTest(type, settings);
    }

    public IEvaluationAttack create(EvaluationAttackTypeEnum type, int numAttackNodes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IEvaluationDeploy create(EvaluationDeployModeEnum mode, int nrNodes, int w, int h) {
        return new PlatformEvaluationDeploy(mode, nrNodes, w, h);
    }
}
