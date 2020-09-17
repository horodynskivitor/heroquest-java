package br.ic.unicamp.mc322.engine;

import br.ic.unicamp.mc322.aliveobjects.Barbarian;
import br.ic.unicamp.mc322.aliveobjects.Dwarf;
import br.ic.unicamp.mc322.aliveobjects.Elf;
import br.ic.unicamp.mc322.aliveobjects.Goblin;
import br.ic.unicamp.mc322.aliveobjects.Skeleton;
import br.ic.unicamp.mc322.aliveobjects.SkeletonWizard;
import br.ic.unicamp.mc322.aliveobjects.Wizard;
import br.ic.unicamp.mc322.map.Door;
import br.ic.unicamp.mc322.map.Trap;
import br.ic.unicamp.mc322.map.Treasure;
import br.ic.unicamp.mc322.map.Wall;

public interface MapObjectVisitor {
	public void visit(Barbarian barbarian);

	public void visit(Door door);

	public void visit(Dwarf dwarf);

	public void visit(Elf elf);

	public void visit(Goblin goblin);

	public void visit(Skeleton skeleton);

	public void visit(SkeletonWizard magSkeleton);

	public void visit(Trap trap);

	public void visit(Treasure treasure);

	public void visit(Wall wall);

	public void visit(Wizard wizard);
}