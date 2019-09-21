package ehr;

import ksch.instruction_state_machine.InstructionStateMachine;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestController implements InstructionStateMachine.InstructionStateMachineController {

    private final DummyEventPublisher dummyEventPublisher;

    @Override
    public void handleStateChanged(InstructionStateMachine.State currentState) {
        dummyEventPublisher.publishStateChangeEvent(currentState);
    }
}
