package ext.yongc.plm.part.link;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _WGJPartLink extends wt.fc.Item implements wt.inf.container.WTContained, java.io.Externalizable, wt.type.Typed {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.yongc.plm.part.link.linkResource";
   static final java.lang.String CLASSNAME = WGJPartLink.class.getName();

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String ROLE_AMASTERIDA2A2 = "roleAmasterida2a2";
   static int ROLE_AMASTERIDA2A2_UPPER_LIMIT = -1;
   java.lang.String roleAmasterida2a2;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.lang.String getRoleAmasterida2a2() {
      return roleAmasterida2a2;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setRoleAmasterida2a2(java.lang.String roleAmasterida2a2) throws wt.util.WTPropertyVetoException {
      roleAmasterida2a2Validate(roleAmasterida2a2);
      this.roleAmasterida2a2 = roleAmasterida2a2;
   }
   void roleAmasterida2a2Validate(java.lang.String roleAmasterida2a2) throws wt.util.WTPropertyVetoException {
      if (roleAmasterida2a2 == null || roleAmasterida2a2.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAmasterida2a2") },
               new java.beans.PropertyChangeEvent(this, "roleAmasterida2a2", this.roleAmasterida2a2, roleAmasterida2a2));
      if (ROLE_AMASTERIDA2A2_UPPER_LIMIT < 1) {
         try { ROLE_AMASTERIDA2A2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleAmasterida2a2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_AMASTERIDA2A2_UPPER_LIMIT = 50; }
      }
      if (roleAmasterida2a2 != null && !wt.fc.PersistenceHelper.checkStoredLength(roleAmasterida2a2.toString(), ROLE_AMASTERIDA2A2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAmasterida2a2"), java.lang.String.valueOf(java.lang.Math.min(ROLE_AMASTERIDA2A2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleAmasterida2a2", this.roleAmasterida2a2, roleAmasterida2a2));
   }

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String ROLE_BIDA2A2 = "roleBida2a2";
   static int ROLE_BIDA2A2_UPPER_LIMIT = -1;
   java.lang.String roleBida2a2;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.lang.String getRoleBida2a2() {
      return roleBida2a2;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setRoleBida2a2(java.lang.String roleBida2a2) throws wt.util.WTPropertyVetoException {
      roleBida2a2Validate(roleBida2a2);
      this.roleBida2a2 = roleBida2a2;
   }
   void roleBida2a2Validate(java.lang.String roleBida2a2) throws wt.util.WTPropertyVetoException {
      if (roleBida2a2 == null || roleBida2a2.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBida2a2") },
               new java.beans.PropertyChangeEvent(this, "roleBida2a2", this.roleBida2a2, roleBida2a2));
      if (ROLE_BIDA2A2_UPPER_LIMIT < 1) {
         try { ROLE_BIDA2A2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleBida2a2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_BIDA2A2_UPPER_LIMIT = 50; }
      }
      if (roleBida2a2 != null && !wt.fc.PersistenceHelper.checkStoredLength(roleBida2a2.toString(), ROLE_BIDA2A2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBida2a2"), java.lang.String.valueOf(java.lang.Math.min(ROLE_BIDA2A2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleBida2a2", this.roleBida2a2, roleBida2a2));
   }

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String ROLE_ANUMBER = "roleAnumber";
   static int ROLE_ANUMBER_UPPER_LIMIT = -1;
   java.lang.String roleAnumber;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.lang.String getRoleAnumber() {
      return roleAnumber;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setRoleAnumber(java.lang.String roleAnumber) throws wt.util.WTPropertyVetoException {
      roleAnumberValidate(roleAnumber);
      this.roleAnumber = roleAnumber;
   }
   void roleAnumberValidate(java.lang.String roleAnumber) throws wt.util.WTPropertyVetoException {
      if (roleAnumber == null || roleAnumber.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAnumber") },
               new java.beans.PropertyChangeEvent(this, "roleAnumber", this.roleAnumber, roleAnumber));
      if (ROLE_ANUMBER_UPPER_LIMIT < 1) {
         try { ROLE_ANUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleAnumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_ANUMBER_UPPER_LIMIT = 50; }
      }
      if (roleAnumber != null && !wt.fc.PersistenceHelper.checkStoredLength(roleAnumber.toString(), ROLE_ANUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAnumber"), java.lang.String.valueOf(java.lang.Math.min(ROLE_ANUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleAnumber", this.roleAnumber, roleAnumber));
   }

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String ROLE_ANAME = "roleAname";
   static int ROLE_ANAME_UPPER_LIMIT = -1;
   java.lang.String roleAname;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.lang.String getRoleAname() {
      return roleAname;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setRoleAname(java.lang.String roleAname) throws wt.util.WTPropertyVetoException {
      roleAnameValidate(roleAname);
      this.roleAname = roleAname;
   }
   void roleAnameValidate(java.lang.String roleAname) throws wt.util.WTPropertyVetoException {
      if (roleAname == null || roleAname.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAname") },
               new java.beans.PropertyChangeEvent(this, "roleAname", this.roleAname, roleAname));
      if (ROLE_ANAME_UPPER_LIMIT < 1) {
         try { ROLE_ANAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleAname").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_ANAME_UPPER_LIMIT = 50; }
      }
      if (roleAname != null && !wt.fc.PersistenceHelper.checkStoredLength(roleAname.toString(), ROLE_ANAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAname"), java.lang.String.valueOf(java.lang.Math.min(ROLE_ANAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleAname", this.roleAname, roleAname));
   }

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String ROLE_BNUMBER = "roleBnumber";
   static int ROLE_BNUMBER_UPPER_LIMIT = -1;
   java.lang.String roleBnumber;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.lang.String getRoleBnumber() {
      return roleBnumber;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setRoleBnumber(java.lang.String roleBnumber) throws wt.util.WTPropertyVetoException {
      roleBnumberValidate(roleBnumber);
      this.roleBnumber = roleBnumber;
   }
   void roleBnumberValidate(java.lang.String roleBnumber) throws wt.util.WTPropertyVetoException {
      if (roleBnumber == null || roleBnumber.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBnumber") },
               new java.beans.PropertyChangeEvent(this, "roleBnumber", this.roleBnumber, roleBnumber));
      if (ROLE_BNUMBER_UPPER_LIMIT < 1) {
         try { ROLE_BNUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleBnumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_BNUMBER_UPPER_LIMIT = 50; }
      }
      if (roleBnumber != null && !wt.fc.PersistenceHelper.checkStoredLength(roleBnumber.toString(), ROLE_BNUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBnumber"), java.lang.String.valueOf(java.lang.Math.min(ROLE_BNUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleBnumber", this.roleBnumber, roleBnumber));
   }

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String ROLE_BNAME = "roleBname";
   static int ROLE_BNAME_UPPER_LIMIT = -1;
   java.lang.String roleBname;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.lang.String getRoleBname() {
      return roleBname;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setRoleBname(java.lang.String roleBname) throws wt.util.WTPropertyVetoException {
      roleBnameValidate(roleBname);
      this.roleBname = roleBname;
   }
   void roleBnameValidate(java.lang.String roleBname) throws wt.util.WTPropertyVetoException {
      if (roleBname == null || roleBname.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBname") },
               new java.beans.PropertyChangeEvent(this, "roleBname", this.roleBname, roleBname));
      if (ROLE_BNAME_UPPER_LIMIT < 1) {
         try { ROLE_BNAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleBname").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_BNAME_UPPER_LIMIT = 50; }
      }
      if (roleBname != null && !wt.fc.PersistenceHelper.checkStoredLength(roleBname.toString(), ROLE_BNAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBname"), java.lang.String.valueOf(java.lang.Math.min(ROLE_BNAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleBname", this.roleBname, roleBname));
   }

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String CREATE_BY = "createBy";
   static int CREATE_BY_UPPER_LIMIT = -1;
   java.lang.String createBy;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.lang.String getCreateBy() {
      return createBy;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setCreateBy(java.lang.String createBy) throws wt.util.WTPropertyVetoException {
      createByValidate(createBy);
      this.createBy = createBy;
   }
   void createByValidate(java.lang.String createBy) throws wt.util.WTPropertyVetoException {
      if (CREATE_BY_UPPER_LIMIT < 1) {
         try { CREATE_BY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("createBy").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATE_BY_UPPER_LIMIT = 50; }
      }
      if (createBy != null && !wt.fc.PersistenceHelper.checkStoredLength(createBy.toString(), CREATE_BY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "createBy"), java.lang.String.valueOf(java.lang.Math.min(CREATE_BY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "createBy", this.createBy, createBy));
   }

   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public static final java.lang.String CREATE_TIME = "createTime";
   static int CREATE_TIME_UPPER_LIMIT = -1;
   java.sql.Timestamp createTime;
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public java.sql.Timestamp getCreateTime() {
      return createTime;
   }
   /**
    * @see ext.yongc.plm.part.link.WGJPartLink
    */
   public void setCreateTime(java.sql.Timestamp createTime) throws wt.util.WTPropertyVetoException {
      createTimeValidate(createTime);
      this.createTime = createTime;
   }
   void createTimeValidate(java.sql.Timestamp createTime) throws wt.util.WTPropertyVetoException {
      if (CREATE_TIME_UPPER_LIMIT < 1) {
         try { CREATE_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("createTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATE_TIME_UPPER_LIMIT = 50; }
      }
      if (createTime != null && !wt.fc.PersistenceHelper.checkStoredLength(createTime.toString(), CREATE_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "createTime"), java.lang.String.valueOf(java.lang.Math.min(CREATE_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "createTime", this.createTime, createTime));
   }

   /**
    * Derived from {@link wt.inf.container.WTContainerRef#getName()}
    *
    * @see wt.inf.container.WTContained
    */
   public java.lang.String getContainerName() {
      try { return (java.lang.String) ((wt.inf.container.WTContainerRef) getContainerReference()).getName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   wt.inf.container.WTContainerRef containerReference;
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainer getContainer() {
      return (containerReference != null) ? (wt.inf.container.WTContainer) containerReference.getObject() : null;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainerRef getContainerReference() {
      return containerReference;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainer(wt.inf.container.WTContainer the_container) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setContainerReference(the_container == null ? null : wt.inf.container.WTContainerRef.newWTContainerRef((wt.inf.container.WTContainer) the_container));
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainerReference(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      containerReferenceValidate(the_containerReference);
      containerReference = (wt.inf.container.WTContainerRef) the_containerReference;
   }
   void containerReferenceValidate(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      if (the_containerReference == null || the_containerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference") },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
      if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
            !wt.inf.container.WTContainer.class.isAssignableFrom(the_containerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference"), "WTContainerRef" },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
   }

   wt.type.TypeDefinitionReference typeDefinitionReference;
   /**
    * @see wt.type.Typed
    */
   public wt.type.TypeDefinitionReference getTypeDefinitionReference() {
      return typeDefinitionReference;
   }
   /**
    * @see wt.type.Typed
    */
   public void setTypeDefinitionReference(wt.type.TypeDefinitionReference typeDefinitionReference) throws wt.util.WTPropertyVetoException {
      typeDefinitionReferenceValidate(typeDefinitionReference);
      this.typeDefinitionReference = typeDefinitionReference;
   }
   void typeDefinitionReferenceValidate(wt.type.TypeDefinitionReference typeDefinitionReference) throws wt.util.WTPropertyVetoException {
      if (typeDefinitionReference == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeDefinitionReference") },
               new java.beans.PropertyChangeEvent(this, "typeDefinitionReference", this.typeDefinitionReference, typeDefinitionReference));
   }

   wt.iba.value.AttributeContainer theAttributeContainer;
   /**
    * @see wt.iba.value.IBAHolder
    */
   public wt.iba.value.AttributeContainer getAttributeContainer() {
      return theAttributeContainer;
   }
   /**
    * @see wt.iba.value.IBAHolder
    */
   public void setAttributeContainer(wt.iba.value.AttributeContainer theAttributeContainer) {
      this.theAttributeContainer = theAttributeContainer;
   }

   public java.lang.String getConceptualClassname() {
      return CLASSNAME;
   }

   public wt.introspection.ClassInfo getClassInfo() throws wt.introspection.WTIntrospectionException {
      return wt.introspection.WTIntrospector.getClassInfo(getConceptualClassname());
   }

   public java.lang.String getType() {
      try { return getClassInfo().getDisplayName(); }
      catch (wt.introspection.WTIntrospectionException wte) { return wt.util.WTStringUtilities.tail(getConceptualClassname(), '.'); }
   }

   public static final long EXTERNALIZATION_VERSION_UID = -4304452216500544085L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( containerReference );
      output.writeObject( createBy );
      output.writeObject( createTime );
      output.writeObject( roleAmasterida2a2 );
      output.writeObject( roleAname );
      output.writeObject( roleAnumber );
      output.writeObject( roleBida2a2 );
      output.writeObject( roleBname );
      output.writeObject( roleBnumber );
      output.writeObject( typeDefinitionReference );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( theAttributeContainer );
      }

   }

   protected void super_writeExternal_WGJPartLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.yongc.plm.part.link.WGJPartLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_WGJPartLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "createBy", createBy );
      output.setTimestamp( "createTime", createTime );
      output.setString( "roleAmasterida2a2", roleAmasterida2a2 );
      output.setString( "roleAname", roleAname );
      output.setString( "roleAnumber", roleAnumber );
      output.setString( "roleBida2a2", roleBida2a2 );
      output.setString( "roleBname", roleBname );
      output.setString( "roleBnumber", roleBnumber );
      output.writeObject( "typeDefinitionReference", typeDefinitionReference, wt.type.TypeDefinitionReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      createBy = input.getString( "createBy" );
      createTime = input.getTimestamp( "createTime" );
      roleAmasterida2a2 = input.getString( "roleAmasterida2a2" );
      roleAname = input.getString( "roleAname" );
      roleAnumber = input.getString( "roleAnumber" );
      roleBida2a2 = input.getString( "roleBida2a2" );
      roleBname = input.getString( "roleBname" );
      roleBnumber = input.getString( "roleBnumber" );
      typeDefinitionReference = (wt.type.TypeDefinitionReference) input.readObject( "typeDefinitionReference", typeDefinitionReference, wt.type.TypeDefinitionReference.class, true );
   }

   boolean readVersion_4304452216500544085L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      createBy = (java.lang.String) input.readObject();
      createTime = (java.sql.Timestamp) input.readObject();
      roleAmasterida2a2 = (java.lang.String) input.readObject();
      roleAname = (java.lang.String) input.readObject();
      roleAnumber = (java.lang.String) input.readObject();
      roleBida2a2 = (java.lang.String) input.readObject();
      roleBname = (java.lang.String) input.readObject();
      roleBnumber = (java.lang.String) input.readObject();
      typeDefinitionReference = (wt.type.TypeDefinitionReference) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            theAttributeContainer = (wt.iba.value.AttributeContainer) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( WGJPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4304452216500544085L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_WGJPartLink( _WGJPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
