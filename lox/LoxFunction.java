package lox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function delcaration;
    private final Environment closure;

    LoxFunction(Stmt.Function declaration, Environment closure) {
        this.delcaration = declaration;
        this.closure = closure;
    }

    @Override
    public String toString() {
        return "<fn " + delcaration.name.lexeme + ">";
    }

    @Override
    public int arity() {
        return delcaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < delcaration.params.size(); i++) {
            environment.define(delcaration.params.get(i).lexeme, arguments.get(i));
        }

        try {
            interpreter.executeBlock(delcaration.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        return null;
    }
}


