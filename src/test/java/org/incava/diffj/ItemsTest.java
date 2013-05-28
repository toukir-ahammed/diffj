package org.incava.diffj;

import org.incava.analysis.FileDiff;
import org.incava.analysis.FileDiffAdd;
import org.incava.analysis.FileDiffChange;
import org.incava.analysis.FileDiffDelete;
import org.incava.diffj.compunit.Package;
import org.incava.diffj.element.Access;
import org.incava.diffj.element.Modifiers;
import org.incava.diffj.field.Variable;
import org.incava.diffj.field.Variables;
import org.incava.diffj.function.Ctor;
import org.incava.diffj.function.Method;
import org.incava.diffj.type.Type;
import org.incava.diffj.type.Types;
import org.incava.ijdk.text.Location;
import org.incava.ijdk.text.LocationRange;
import org.incava.ijdk.text.Message;

public class ItemsTest extends DiffJTest {
    protected final static Message[] METHOD_MSGS = new Message[] {
        Method.METHOD_REMOVED,
        Method.METHOD_CHANGED, 
        Method.METHOD_ADDED,
    };

    protected final static Message[] CLASS_MSGS = new Message[] {
        Type.INNER_CLASS_REMOVED,
        null,
        Type.INNER_CLASS_ADDED,
    };

    protected final static Message[] INTERFACE_MSGS = new Message[] {
        Type.INNER_INTERFACE_REMOVED,
        null,
        Type.INNER_INTERFACE_ADDED,
    };

    protected final static Message[] CONSTRUCTOR_MSGS = new Message[] {
        Ctor.CONSTRUCTOR_REMOVED,
        null,
        Ctor.CONSTRUCTOR_ADDED,
    };

    protected final static Message[] MODIFIER_MSGS = new Message[] {
        Modifiers.MODIFIER_REMOVED,
        Modifiers.MODIFIER_CHANGED,
        Modifiers.MODIFIER_ADDED,
    };

    protected final static Message[] ACCESS_MSGS = new Message[] {
        Access.ACCESS_REMOVED,
        Access.ACCESS_CHANGED, 
        Access.ACCESS_ADDED,
    };

    protected final static Message[] TYPES_MSGS = new Message[] {
        Types.TYPE_DECLARATION_REMOVED,
        null,
        Types.TYPE_DECLARATION_ADDED,
    };

    protected final static Message[] PACKAGE_MSGS = new Message[] {
        Package.PACKAGE_REMOVED, 
        Package.PACKAGE_RENAMED,
        Package.PACKAGE_ADDED,
    };

    protected final static Message[] VARIABLE_MSGS = new Message[] {
        Variables.VARIABLE_REMOVED,
        Variables.VARIABLE_CHANGED, 
        Variables.VARIABLE_ADDED,
    };

    public ItemsTest(String name) {
        super(name);
    }

    //$$$ todo: migrate (Location, Location) to (LocationRange)
    //$$$ todo: move interfaceRef, etc, to TestInterface

    protected FileDiff makeMethodRef(String from, String to, LocationRange fromLoc, LocationRange toLoc) {
        return makeRef(from, to, METHOD_MSGS, fromLoc, toLoc);
    }

    protected FileDiff makeInterfaceRef(String from, String to, LocationRange fromLoc, LocationRange toLoc) {
        return makeRef(from, to, INTERFACE_MSGS, fromLoc, toLoc);
    }

    protected FileDiff makeRef(String from, String to, Message[] msgs, LocationRange fromLoc, LocationRange toLoc) {
        if (to == null) {
            return new FileDiffDelete(fromLoc, toLoc, msgs[0], from);
        }
        else if (from == null) {
            return new FileDiffAdd(fromLoc, toLoc, msgs[2], to);
        }
        else {
            return new FileDiffChange(fromLoc, toLoc, msgs[1], from, to);
        }
    }

    protected String getMessage(Message removedMsg, Message addedMsg, Message changedMsg, String from, String to) {
        if (to == null) {
            return removedMsg.format(from);
        }
        else if (from == null) {
            return addedMsg.format(to);
        }
        else {
            return changedMsg.format(from, to);
        }
    }

    protected FileDiff makeChangedRef(String from, String to, Message[] msgs, LocationRange fromLoc, LocationRange toLoc) {
        if (to == null) {
            return new FileDiffChange(fromLoc, toLoc, msgs[0], from);
        }
        else if (from == null) {
            return new FileDiffChange(fromLoc, toLoc, msgs[2], to);
        }
        else {
            return new FileDiffChange(fromLoc, toLoc, msgs[1], from, to);
        }
    }

    protected FileDiff makeAccessRef(String from, String to, Location fromStart, Location toStart) {
        return makeChangedRef(from, to, ACCESS_MSGS, locrg(fromStart, from), locrg(toStart, to));
    }

    protected FileDiff makeAccessRef(String from, String to, LocationRange fromLoc, LocationRange toLoc) {
        return makeChangedRef(from, to, ACCESS_MSGS, fromLoc, toLoc);
    }    

    protected FileDiff makeModifierRef(String from, String to, LocationRange fromLoc, LocationRange toLoc) {
        return makeChangedRef(from, to, MODIFIER_MSGS, fromLoc, toLoc);
    }

    protected FileDiff makeCodeChangedRef(Message codeChgMsg, String where, LocationRange fromLoc, LocationRange toLoc) {
        return makeCodeChangedRef(codeChgMsg, new String[] { where }, fromLoc, toLoc);
    }

    protected FileDiff makeCodeChangedRef(Message codeChgMsg, String[] args, LocationRange fromLoc, LocationRange toLoc) {
        return new FileDiffChange(fromLoc, toLoc, codeChgMsg, (Object[])args);
    }

    protected FileDiff makeCodeChangedRef(LocationRange fromLoc, LocationRange toLoc, Message codeChgMsg, String ... args) {
        return new FileDiffChange(fromLoc, toLoc, codeChgMsg, (Object[])args);
    }

    protected FileDiff makeCodeAddedRef(Message codeChgMsg, String where, LocationRange fromLoc, LocationRange toLoc) {
        return makeCodeAddedRef(codeChgMsg, new String[] { where }, fromLoc, toLoc);
    }

    protected FileDiff makeCodeAddedRef(Message codeChgMsg, String[] args, LocationRange fromLoc, LocationRange toLoc) {
        return new FileDiffAdd(fromLoc, toLoc, codeChgMsg, (Object[])args);
    }

    protected FileDiff makeCodeDeletedRef(Message codeChgMsg, String where, LocationRange fromLoc, LocationRange toLoc) {
        return makeCodeDeletedRef(codeChgMsg, new String[] { where }, fromLoc, toLoc);
    }

    protected FileDiff makeCodeDeletedRef(Message codeChgMsg, String[] args, LocationRange fromLoc, LocationRange toLoc) {
        return new FileDiffDelete(fromLoc, toLoc, codeChgMsg, (Object[])args);
    }
}
