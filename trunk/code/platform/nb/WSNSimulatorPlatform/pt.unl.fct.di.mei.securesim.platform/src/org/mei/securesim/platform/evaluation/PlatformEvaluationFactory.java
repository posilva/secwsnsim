/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.platform.evaluation;

import org.mei.securesim.components.evaluation.EvaluationAttackTypeEnum;
import org.mei.securesim.components.evaluation.EvaluationDeployModeEnum;
import org.mei.securesim.components.evaluation.EvaluationTestSettings;
import org.mei.securesim.components.evaluation.EvaluationTestTypeEnum;
import org.mei.securesim.components.evaluation.IEvaluationAttack;
import org.mei.securesim.components.evaluation.IEvaluationDeploy;
import org.mei.securesim.components.evaluation.IEvaluationFactory;
import org.mei.securesim.components.evaluation.IEvaluationTest;

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
