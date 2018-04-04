package base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * This is an List with some Listeners
 */
public class ListenerList<E> extends ArrayList<E>{
    
    @Override
    public boolean add(E element) {
        boolean elementRemoved = super.add(element);
        elementAddedListener.act();
        anyChangeListener.act();
        return elementRemoved;
    
    }
    
    @Override
    public void add(int index, E element) {
        super.add(index, element);
        elementAddedListener.act();
        anyChangeListener.act();
    
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean elementAdded = super.addAll(c);
        if(elementAdded) {
            elementAddedListener.act();
            anyChangeListener.act();
        }
        return elementAdded;
    
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean elementAdded = super.addAll(index, c);
        if(elementAdded) {
            elementAddedListener.act();
            anyChangeListener.act();
        }
        return elementAdded;
        
    }
    
    @Override
    public void clear() {
        super.clear();
        elementRemovedListener.act();
        anyChangeListener.act();
        
    }
    
    @Override
    public E remove(int index) {
        E elementRemoved = super.remove(index);
        elementRemovedListener.act();
        anyChangeListener.act();
        return elementRemoved;
    
    }
    
    @Override
    public boolean remove(Object o) {
        boolean elementRemoved = super.remove(o);
        if(elementRemoved) {
            elementRemovedListener.act();
            anyChangeListener.act();
        }
        return elementRemoved;
    
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean elementRemoved = super.removeAll(c);
        if(elementRemoved) {
            elementRemovedListener.act();
            anyChangeListener.act();
        }
        return elementRemoved;
    
    }
    
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        boolean elementRemoved = super.removeIf(filter);
        if(elementRemoved) {
            elementRemovedListener.act();
            anyChangeListener.act();
        }
        return elementRemoved;
    
    }
    
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        super.replaceAll(operator);
        elementSetListener.act();
        anyChangeListener.act();
        
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean elementRemoved = super.retainAll(c);
        if(elementRemoved) {
            elementRemovedListener.act();
            anyChangeListener.act();
        }
        return elementRemoved;
    
    }
    
    @Override
    public E set(int index, E element) {
        E setElement = super.set(index, element);
        elementSetListener.act();
        anyChangeListener.act();
        return setElement;
    
    }
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Listeners///////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
    public final Listener elementAddedListener = new Listener();
    public final Listener elementRemovedListener = new Listener();
    public final Listener elementSetListener = new Listener();
    public final Listener anyChangeListener = new Listener();
            
    /**
     * This class makes Listeners for this listenerList
     */
    public class Listener{
        
        private Set<Action> actionSet;
        
        Listener() {
            actionSet = new HashSet<>();
        
        }
        
        /**
         * Adds an Action that will be executed when the property that the
         * listener is listening to changes. The same Action Object can only be
         * added once.
         * @param action The Action to be added.
         */
        public void add(Action action) {
            actionSet.add(action);
            
        }
        
        /**
         * Removes an Action. It will no longer be executed when the property 
         * that the listener is listening to changes.
         * @param action The action to remove.
         */
        public void remove(Action action) {
            actionSet.remove(action);
        
        }
        
        //method this listenerList needs to call when the property changes
        private void act() {
            actionSet.forEach(Action::act);
            
        }
            
    
    }

}
