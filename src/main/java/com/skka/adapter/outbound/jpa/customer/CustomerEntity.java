package com.skka.adapter.outbound.jpa.customer;

import static com.skka.adapter.common.exception.ErrorType.INVALID_CUSTOMER_EMAIL;
import static com.skka.adapter.common.exception.ErrorType.INVALID_CUSTOMER_NAME;
import static com.skka.adapter.common.exception.ErrorType.INVALID_CUSTOMER_TEL;
import static com.skka.adapter.util.Util.require;

import com.skka.adapter.outbound.jpa.BaseEntity;
import com.skka.adapter.outbound.jpa.studyseat.schedule.ScheduleEntity;
import com.skka.domain.customer.Customer;
import com.skka.domain.studyseat.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class CustomerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String password;
    private String tel;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleEntity> schedules = new ArrayList<>();

    private CustomerEntity(
        final long id,
        final String name,
        final String email,
        final String password,
        final String tel
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public static CustomerEntity of(
        final long id,
        final String name,
        final String email,
        final String password,
        final String tel
    ) {

        require(o -> name == null, name, INVALID_CUSTOMER_NAME);
        require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
        require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);

        return new CustomerEntity(id, name, email, password, tel);
    }

    public void setScheduleFromDomainToEntity(ScheduleEntity schedule) {
        this.schedules.add(schedule);
    }
    public Customer toCustomer() {
        return Customer.of(id, name, email, password, tel, getLastModified(), getCreatedAt());
    }

    public Customer toCustomerReturn() {
        Customer customer = Customer.of(id, name, email, password, tel, getLastModified(), getCreatedAt());
        schedules.forEach(scheduleEntity -> customer.getSchedules().add(
            Schedule.of(
                scheduleEntity.getId(),
                scheduleEntity.getCustomer().toCustomer(),
                scheduleEntity.getStudySeat().toStudySeat(),
                scheduleEntity.getStartedTime(),
                scheduleEntity.getEndTime()
            )
        ));

        return customer;
    }
}
