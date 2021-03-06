package com.example;

import java.util.HashMap;

public class Timetable {
	private final HashMap<Integer, Room> rooms;
	private final HashMap<Integer, Professor> professors;
	private final HashMap<Integer, Module> modules;
	private final HashMap<Integer, Group> groups;
	private final HashMap<Integer, Timeslot> timeslots;
	private Class classes[];

	private int numClasses = 0;

	/**
	 * Initialize new Timetable
	 */
	public Timetable() {
		this.rooms = new HashMap<Integer, Room>();
		this.professors = new HashMap<Integer, Professor>();
		this.modules = new HashMap<Integer, Module>();
		this.groups = new HashMap<Integer, Group>();
		this.timeslots = new HashMap<Integer, Timeslot>();
	}

	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.professors = cloneable.getProfessors();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
		this.timeslots = cloneable.getTimeslots();
	}

	private HashMap<Integer, Group> getGroups() {
		return this.groups;
	}

	private HashMap<Integer, Timeslot> getTimeslots() {
		return this.timeslots;
	}

	private HashMap<Integer, Module> getModules() {
		return this.modules;
	}

	public HashMap<Integer, Professor> getProfessors() {
		return this.professors;
	}

	/**
	 * Add new room
	 * 
	 * @param roomId
	 * @param roomName
	 * @param capacity
	 */
	public void addRoom(int roomId, String roomName, int capacity) {
		this.rooms.put(roomId, new Room(roomId, roomName, capacity));
	}

	/**
	 * Add new professor
	 * 
	 * @param professorId
	 * @param professorName
	 */
	public void addProfessor(int professorId, String professorName) {
		this.professors.put(professorId, new Professor(professorId, professorName));
	}

	public void addProfessor(Professor professor) {
		this.professors.put(professor.getProfessorId(), professor);
	}

	/**
	 * Add new module
	 * 
	 * @param moduleId
	 * @param moduleCode
	 * @param module
	 * @param professorIds
	 */
	public void addModule(int moduleId, String moduleCode, String module, int professorIds[]) {
		this.modules.put(moduleId, new Module(moduleId, moduleCode, module, professorIds));
	}

	public void addModule(Module newModule) {
		this.modules.put(newModule.getModuleId(), newModule);
	}

	/**
	 * Add new group
	 * 
	 * @param groupId
	 * @param groupSize
	 * @param moduleIds
	 */
	public void addGroup(int groupId, int groupSize, int moduleIds[]) {
		this.groups.put(groupId, new Group(groupId, groupSize, moduleIds));
		this.numClasses = 0;
	}

	/**
	 * Add new timeslot
	 * 
	 * @param timeslotId
	 * @param timeslot
	 */
	public void addTimeslot(int timeslotId, String timeslot) {
		this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot));
	}

	public void addTimeslot(int timeslotId, String timeslot, int[] m, int[] t, int[] w, int[] th, int[] f) {
		this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot, m, t, w, th, f));
	}

	public void addTimeslot(Timeslot ts) {
		this.timeslots.put(ts.getTimeslotId(), ts);
	}

	public void createClasses(Individual individual) {
		// Init classes
		Class classes[] = new Class[this.getNumClasses()];

		// Get individual's chromosome
		int chromosome[] = individual.getChromosome();
		int chromosomePos = 0;
		int classIndex = 0;

		for (Group group : this.getGroupsAsArray()) {
			int moduleIds[] = group.getModuleIds();
			for (int moduleId : moduleIds) {
				classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);

				// Add timeslot
				classes[classIndex].addTimeslot(chromosome[chromosomePos]);
				chromosomePos++;

				// Add room
				classes[classIndex].setRoomId(chromosome[chromosomePos]);
				chromosomePos++;

				// Add professor
				classes[classIndex].addProfessor(chromosome[chromosomePos]);
				chromosomePos++;

				classIndex++;
			}
		}

		this.classes = classes;
	}

	/**
	 * Get room from roomId
	 * 
	 * @param roomId
	 * @return room
	 */
	public Room getRoom(int roomId) {
		if (!this.rooms.containsKey(roomId)) {
			System.out.println("Rooms doesn't contain key " + roomId);
		}
		return (Room) this.rooms.get(roomId);
	}

	public HashMap<Integer, Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Get random room
	 * 
	 * @return room
	 */
	public Room getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}

	/**
	 * Get professor from professorId
	 * 
	 * @param professorId
	 * @return professor
	 */
	public Professor getProfessor(int professorId) {
		return (Professor) this.professors.get(professorId);
	}

	/**
	 * Get module from moduleId
	 * 
	 * @param moduleId
	 * @return module
	 */
	public Module getModule(int moduleId) {
		return (Module) this.modules.get(moduleId);
	}

	/**
	 * Get moduleIds of student group
	 * 
	 * @param groupId
	 * @return moduleId array
	 */
	public int[] getGroupModules(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getModuleIds();
	}

	/**
	 * Get group from groupId
	 * 
	 * @param groupId
	 * @return group
	 */
	public Group getGroup(int groupId) {
		return (Group) this.groups.get(groupId);
	}

	/**
	 * Get all student groups
	 * 
	 * @return array of groups
	 */
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}

	public int[] getModuleIds() {
		Module[] mods = this.modules.values().toArray(new Module[this.modules.size()]);
		int[] temp = new int[this.modules.size()];
		int idx = 0;
		for (Module m : mods) {
			temp[idx] = m.getModuleId();
			idx++;
		}
		return temp;
	}

	public Professor[] getProfessorsAsArray() {
		return (Professor[]) this.professors.values().toArray(new Professor[this.professors.size()]);
	}

	/**
	 * Get timeslot by timeslotId
	 * 
	 * @param timeslotId
	 * @return timeslot
	 */
	public Timeslot getTimeslot(int timeslotId) {
		return (Timeslot) this.timeslots.get(timeslotId);
	}

	/**
	 * Get random timeslotId
	 * 
	 * @return timeslot
	 */
	public Timeslot getRandomTimeslot() {
		Object[] timeslotArray = this.timeslots.values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}

	/**
	 * Get classes
	 * 
	 * @return classes
	 */
	public Class[] getClasses() {
		return this.classes;
	}

	/**
	 * Get number of classes that need scheduling
	 * 
	 * @return numClasses
	 */
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}

		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getModuleIds().length;
		}
		this.numClasses = numClasses;

		return this.numClasses;
	}

	public int calcClashes(Timetable timetable) {
		int clashes = 0;
		Professor[] profs = this.getProfessorsAsArray();
		HashMap<Integer, Integer> profCounts = new HashMap<Integer, Integer>();
		for (int i = 0; i < profs.length; i++) {
			profCounts.put(profs[i].getProfessorId(), 0);
		}

		for (Class classA : this.classes) {
			// get current prof assigned to check for overlaps
			for (Class classb : this.classes) {
				if (classb.getProfessorId() == classA.getProfessorId()) {
					Timeslot ts1 = this.getTimeslot(classA.getTimeslotId());
					int ts2Id = classb.getTimeslotId();
					if (ts1.avoid == null) {
						break;
					} else {
						for (int i = 0; i < ts1.avoid.length; i++) {
							if (ts1.avoid[i] == ts2Id) {
								clashes++;
								break;
							}
						}
					}
				}
			}

			// if professor is in HashMap update count

			int current = profCounts.get(classA.getProfessorId());
			current++;
			profCounts.put(classA.getProfessorId(), current);

			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomId()).getRoomCapacity();
			int groupSize = this.getGroup(classA.getGroupId()).getGroupSize();

			if (roomCapacity < groupSize) {
				clashes++;
			}

			// Check if room is taken
			for (Class classB : this.classes) {
				if (classA.getRoomId() == classB.getRoomId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}

			// Check if professor is available
			for (Class classB : this.classes) {
				if (classA.getProfessorId() == classB.getProfessorId()
						&& classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}

		}

		// chech if there is conflicting times

		// compare each porfessor's assigned classes with max

		for (int i = 0; i < profs.length; i++) {
			int maxLimit = profs[i].getMaxClasses();
			int currentAssigned = profCounts.get(profs[i].getProfessorId());
			// System.out.println(String.format("%s, max: %d, assigned: %d",
			// profs[i].getProfessorName(), maxLimit, currentAssigned )) ;
			if (currentAssigned > maxLimit) {
				clashes++;
			} else if (currentAssigned == 0) {
				clashes++;
			}
		}

		return clashes;
	}
}